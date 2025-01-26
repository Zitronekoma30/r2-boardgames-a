package com.boardgame.core.model.event;

import com.boardgame.core.GameManager;
import com.boardgame.core.Player;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class Event implements HttpHandler {
    private String endpoint;
    private GameManager manager;

    public Event(String name){
        this.endpoint = name.toLowerCase();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setManager(GameManager manager){
        this.manager = manager;
    }

    public abstract String onTrigger(Player player, String str);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read the request body
            InputStream is = exchange.getRequestBody();
            String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Parse JSON
            JSONObject jsonObject = new JSONObject(requestBody);
            String playerId = jsonObject.getString("playerId");
            String str = jsonObject.has("str") ? jsonObject.getString("str") : null;
            Player player = manager.getPlayerById(playerId);

            // Write the response
            if (player == null || manager.getCurrentPlayer() != player){
                exchange.sendResponseHeaders(403, 0); // Access Denied
                return;
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("response", onTrigger(player, str));
            String response = responseJson.toString();

            exchange.sendResponseHeaders(200, 0); // OK
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

}
