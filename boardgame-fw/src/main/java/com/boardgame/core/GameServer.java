package com.boardgame.core;

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
import java.util.ArrayList;

import org.json.JSONObject;

public class GameServer {
    private final GameManagerFactory factory;
    private final String url;
    private final int port;
    private final String frontEndPath;

    private ArrayList<GameManager> games;

    public GameServer(GameManagerFactory factory, String url, int port, String frontEndPath) {
        this.factory = factory;
        this.url = url;
        this.port = port;
        this.frontEndPath = frontEndPath;

        games = new ArrayList<>();
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
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/res", new StaticFileHandler(frontEndPath));

        var man = factory.produceGameManager();
        games.add(man);
        man.startGame();

        server.createContext("/join", new JoinHandler(games.getFirst()));
        server.createContext("/board", new BoardHandler(games.getFirst()));
        server.createContext("/move", wrapWithCors(new MoveHandler(games.getFirst())));
        server.createContext("/get-hand", wrapWithCors(new GetHandHandler(games.getFirst())));
        server.createContext("/play-hand", wrapWithCors(new PlayHandHandler(games.getFirst())));
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on " + url + ":" + port + "/ serving files from " + frontEndPath);
    }

    private void addCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        String localFrontEndURL = "http://localhost:3000";
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
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } catch (IOException e) {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        }
    }


    private abstract class GameHttpHandler implements HttpHandler {
        protected GameManager manager;

        public GameHttpHandler(GameManager manager){
            this.manager = manager;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
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

    private class JoinHandler extends GameHttpHandler {
        public JoinHandler(GameManager manager) {
            super(manager);
        }

        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            try {
                if ("GET".equals(exchange.getRequestMethod())) {
                    String playerId = manager.joinGame();
                    System.out.println("Player " + playerId + " joined the game");
                    exchange.sendResponseHeaders(200, playerId.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(playerId.getBytes(StandardCharsets.UTF_8));
                    os.close();
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            } catch (Exception e) {
                System.out.println("Error while joining the game: " + e.getMessage());
                exchange.sendResponseHeaders(500, -1); // Internal Server Error
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
                System.out.println(player.getId() + " hand requested: ");
                System.out.println(response);

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
                JSONObject jsonObject = new JSONObject(requestBody);

                int handIdx = jsonObject.getInt("handIdx");
                int toX = jsonObject.getInt("toX");
                int toY = jsonObject.getInt("toY");
                String playerId = jsonObject.getString("playerId");

                Player player = manager.getPlayerById(playerId);

                // Perform the move
                if (player == null) {
                    exchange.sendResponseHeaders(403, 0);
                    return;
                }

                Tile tile = manager.getBoard().getTile(toX, toY);
                player.playPieceFromHand(handIdx, tile);

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