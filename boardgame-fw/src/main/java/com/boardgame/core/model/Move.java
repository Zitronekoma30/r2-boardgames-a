package com.boardgame.core.model;

import com.boardgame.core.GameBoard;
import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.Tile;
import org.json.JSONObject;

public class Move {
    private Tile from;
    private Tile to;
    private GamePiece piece;

    public static Move parseMoveFromJson(String json, GameBoard board) {
        JSONObject jsonObject = new JSONObject(json);

        // JSON has fields: fromX, fromY, toX, toY, and pieceType
        int fromX = jsonObject.getInt("fromX");
        int fromY = jsonObject.getInt("fromY");
        int toX = jsonObject.getInt("toX");
        int toY = jsonObject.getInt("toY");
        String playerId = jsonObject.getString("playerId");

        Tile fromTile = board.getTile(fromX, fromY);
        Tile toTile = board.getTile(toX, toY);
        //GamePiece piece = GameObjectRegistry.getInstance().createPiece(pieceType);

        return new Move(fromTile, toTile, playerId);
    }

    public Move(Tile from, Tile to, String playerId){
        this.from = from;
        this.to = to;
        this.piece = from.getPieceByPlayer(playerId);
    }

    public Move(Tile from, Tile to, GamePiece piece){
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public Tile getFrom() {
        return from;
    }

    public Tile getTo() {
        return to;
    }

    public GamePiece getPiece() {
        return piece;
    }

    public Boolean isValid(){
        return piece != null && to != null && from != null;
    }

    public boolean execute() {
        if (!isValid()) return false;

        return piece.movePiece(to);
    }

    public Player getPlayer() {
        return piece.getOwner();
    }
}
