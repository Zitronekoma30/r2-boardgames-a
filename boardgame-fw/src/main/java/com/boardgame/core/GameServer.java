package com.boardgame.core;

import com.boardgame.core.model.Move;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class GameServer {
    private final GameBoard board;
    private final GameManager manager;
    private final String url;
    private final int port;
    private final String frontEndPath;

    public GameServer(GameManager manager, GameBoard board, String url, int port, String frontEndPath) {
        this.manager = manager;
        this.board = board;
        this.url = url;
        this.port = port;
        this.frontEndPath = frontEndPath;
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/join", new JoinHandler());
        server.createContext("/board", new BoardHandler());
        server.createContext("/move", new MoveHandler());
        server.createContext("/", new StaticFileHandler(frontEndPath));
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on " + url + ":" + port + "/ serving files from " + frontEndPath);
    }

    private void addCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        String localFrontEndURL = "http://localhost:3000";
        headers.add("Access-Control-Allow-Origin", localFrontEndURL); // TODO: Change this if needed
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }

    private class BoardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = board.toJson();
                System.out.println("Board requested");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    private class JoinHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
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

    private class MoveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Parse JSON
                Move move = Move.parseMoveFromJson(requestBody, board);

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

    private class StaticFileHandler implements HttpHandler { // TODO: Test this thoroughly
        private final String basePath;

        public StaticFileHandler(String basePath) {
            this.basePath = basePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            String filePath = basePath + exchange.getRequestURI().getPath();
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (resourceStream != null) {
                byte[] bytes = resourceStream.readAllBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        }
    }
}