package com.boardgame.core.model.move;

import com.boardgame.core.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HandPlay {
    private Action[] actions;

    private HandPlay(Action[] actions) {
        this.actions = actions;
    }

    public static HandPlay parseFromJson(String json, GameManager manager){
        ArrayList<Action> parsedActions = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        // Get the playerId
        String playerId = jsonObject.getString("playerId");
        var player = manager.getPlayerById(playerId);
        if (player == null) return null;

        // Get the moves array
        JSONArray movesArray = jsonObject.getJSONArray("moves");

        for (int i = 0; i < movesArray.length(); i++) {
            JSONObject moveObject = movesArray.getJSONObject(i);

            int handIdx = moveObject.getInt("handIdx");
            int toX = moveObject.getInt("toX");
            int toY = moveObject.getInt("toY");

            Tile to = manager.getBoard().getTile(toX, toY);
            GamePiece piece = player.getPieceFromHand(handIdx);

            if (to == null || piece == null){
                return null;
            }

            parsedActions.add(new Action(handIdx, piece, player, to));

            // Process each move
            System.out.println("Move " + i + ": handIdx=" + handIdx + ", toX=" + toX + ", toY=" + toY);
        }

        HandPlay play = new HandPlay(parsedActions.toArray(new Action[0]));

        return play;
    }

    public record Action(int handIdx, GamePiece piece, Player player, Tile to) {}
}
