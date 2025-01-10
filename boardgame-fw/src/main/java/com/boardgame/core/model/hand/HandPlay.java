package com.boardgame.core.model.hand;

import com.boardgame.core.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class HandPlay {
    private final Action[] actions;
    private final Player player;

    private HandPlay(Action[] actions, Player player) {
        this.actions = actions;
        this.player = player;
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

        return new HandPlay(parsedActions.toArray(new Action[0]), player);
    }

    public boolean isValid(){
        HashSet<Tile> uniqueTiles = new HashSet<>();

        for (Action a : actions){
            // check if any of the pieces would be placed on tile with no more room
            if (a.to().isFull()) return false;

            // Check for duplicate destinations
            if (!uniqueTiles.add(a.to())) return false;
        }

        var player = actions[0].player();
        var board = actions[0].to().getBoard();

        return player.validateHandPlay(actions, board);
    }

    public Player getPlayer() {
        return player;
    }

    public void execute() {
        for (Action a : actions){
            a.piece().playPiece(a.to());
            a.player().removePieceFromHand(a.piece());
        }
    }
}
