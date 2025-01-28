package com.boardgame.core;

import com.boardgame.core.model.event.Event;
import com.boardgame.core.model.hand.HandPlay;
import com.boardgame.core.model.move.Move;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.json.JSONObject;

public class GameServer {
    private final GameManagerFactory factory;
    private final String url;
    private final int port;
    private final String frontEndPath;
    private HttpServer server;

    private HashMap<String, GameManager> games;

    public GameServer(GameManagerFactory factory, String url, int port, String frontEndPath) {
        this.factory = factory;
        this.url = url;
        this.port = port;
        this.frontEndPath = frontEndPath;

        games = new HashMap<>();
    }

    private HttpHandler wrapWithCors(HttpHandler handler) {
        return exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // No Content for preflight requests
                return;
            }
            handler.handle(exchange);
        };
    }

    public void startServer() throws IOException {
        // Global endpoint setup
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/res", new StaticFileHandler(frontEndPath));
        server.createContext("/join", new JoinHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on " + url + ":" + port + "/ serving files from " + frontEndPath);
    }

    private void gameSetup(String gameName) {
        var manager = games.get(gameName);

        server.createContext(manager.addServerContext("/" + gameName + "/board"), new BoardHandler(manager));
        server.createContext(manager.addServerContext("/" + gameName + "/move"), wrapWithCors(new MoveHandler(manager)));
        server.createContext(manager.addServerContext("/" + gameName + "/get-hand"), wrapWithCors(new GetHandHandler(manager)));
        server.createContext(manager.addServerContext("/" + gameName + "/play-hand"), wrapWithCors(new PlayHandHandler(manager)));

        for (Event e : manager.getEvents()){
            server.createContext(manager.addServerContext("/" + gameName + "/" + e.getEndpoint()), wrapWithCors(e));
        }
    }

    private void addCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*"); // Allow all origins
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE"); // Allow all methods
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Add required headers explicitly
    }

    private class StaticFileHandler implements HttpHandler {
        private final String basePath;

        public StaticFileHandler(String basePath) {
            this.basePath = basePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            String filePath = basePath + exchange.getRequestURI().getPath();
            filePath = filePath.replace("/res", "").replace("/", "\\");

            System.out.println(filePath + " requested");
            try (InputStream resourceStream = new FileInputStream(filePath)) {
                byte[] bytes = resourceStream.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", "image/png");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } catch (IOException e) {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        }
    }

    private class JoinHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (!"GET".equals(exchange.getRequestMethod())){
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                return;
            }

            try {
                // Read the params
                String query = exchange.getRequestURI().getQuery();
                HashMap<String, String> queryParams = parseQueryParams(query);

                // Parse game name from parameter
                String gameName = queryParams.get("game");

                if (gameName == null) {
                    System.out.println("malformed join request received");
                    exchange.sendResponseHeaders(400, 0);
                    return;
                }

                // Create game if not already created
                var manager = games.get(gameName);

                if ( manager == null){
                    manager = factory.produceGameManager();
                    games.put(gameName, manager);
                    gameSetup(gameName);
                }

                // Join game
                if (manager.isFull()) {
                    System.out.println("failed to join game " + gameName + ": full");
                    exchange.sendResponseHeaders(403, 0);
                    return;
                }
                String playerId = manager.joinGame();
                System.out.println("Player " + playerId + " joined the game");
                exchange.sendResponseHeaders(200, playerId.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(playerId.getBytes(StandardCharsets.UTF_8));
                os.close();
            } catch (Exception e) {
                System.out.println("Error while joining the game: " + e.getMessage());
                exchange.sendResponseHeaders(500, -1); // Internal Server Error
            }
        }


        private HashMap<String, String> parseQueryParams(String query) {
            HashMap<String, String> queryParams = new HashMap<>();
            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length > 1) {
                        queryParams.put(keyValue[0], keyValue[1]);
                    } else {
                        queryParams.put(keyValue[0], "");
                    }
                }
            }
            return queryParams;
        }
    }

    private abstract class GameHttpHandler implements HttpHandler {
        protected GameManager manager;

        public GameHttpHandler(GameManager manager){
            this.manager = manager;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getResponseHeaders().containsKey("Access-Control-Allow-Origin")) {
                addCorsHeaders(exchange);
            }
            handleRequest(exchange);
        }

        abstract void handleRequest(HttpExchange exchange) throws IOException;
    }

    private class BoardHandler extends GameHttpHandler {

        public BoardHandler(GameManager manager) {
            super(manager);
        }

        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = manager.getBoard().toJson();
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    private class MoveHandler extends GameHttpHandler {

        public MoveHandler(GameManager manager) {
            super(manager);
        }

        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Parse JSON
                System.out.println(requestBody);
                Move move = Move.parseMoveFromJson(requestBody, manager.getBoard());

                // Perform the move
                if (move == null || !manager.executeMove(move)) {
                    exchange.sendResponseHeaders(400, -1); // Bad Request
                    return;
                }

                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
                os.write("Move processed".getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    private class GetHandHandler extends GameHttpHandler {
        public GetHandHandler(GameManager manager) {
            super(manager);
        }

        @Override
        public void handleRequest(HttpExchange exchange) throws IOException{
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Parse JSON
                JSONObject jsonObject = new JSONObject(requestBody);
                String playerId = jsonObject.getString("playerId");

                Player player = manager.getPlayerById(playerId);

                // Write the response
                if (player == null){
                    exchange.sendResponseHeaders(403, 0); // Access Denied
                    return;
                }

                String response = player.handToJson();

                // Server logging
                //System.out.println(player.getId() + " hand requested: ");
                //System.out.println(response);

                exchange.sendResponseHeaders(200, 0); // OK
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    private class PlayHandHandler extends GameHttpHandler {
        public PlayHandHandler(GameManager manager) {
            super(manager);
        }

        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Parse JSON
                HandPlay play = HandPlay.parseFromJson(requestBody, manager);

                // Perform the move
                if (play == null || !manager.executeHandPlay(play)) {
                    exchange.sendResponseHeaders(403, 0);
                    return;
                }

                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
                os.write("Play processed".getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}