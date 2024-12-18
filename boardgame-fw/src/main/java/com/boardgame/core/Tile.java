package com.boardgame.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {
    private final int pieceCapacity;
    private final List<GamePiece> pieces = new ArrayList<>();
    private final int x;
    private final int y;
    protected final GameBoard board;

    private String sprite;

    public Tile(int x, int y, int pieceCapacity, GameBoard board) {
        this.x = x;
        this.y = y;
        this.pieceCapacity = pieceCapacity;
        this.board = board;
    }

    public boolean addPiece(GamePiece piece) {
        if (pieces.size() >= pieceCapacity) {
            return false;
        }
        onPieceEnter(piece);
        pieces.add(piece);
        piece.setTile(this);
        return true;
    }

    public boolean isEmpty(){
        return pieces.isEmpty();
    }

    public boolean hasPiece(GamePiece piece) {
        return pieces.contains(piece);
    }

    public boolean removePiece(GamePiece piece) {
        onPieceExit(piece);
        return pieces.remove(piece);
    }

    public List<GamePiece> getPieces() {
        return new ArrayList<>(pieces); // Defensive copy
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setSprite(String sprite){
        this.sprite = sprite;
    }

    public int getPieceCapacity() {
        return pieceCapacity;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void onPieceEnter(GamePiece piece) {} // new piece not yet in list
    public void onPieceExit(GamePiece piece) {} // piece not yet removed from list

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"tileName\": \"").append(this.getClass().getSimpleName()).append("\",");
        sb.append("\"sprite\": \"").append(sprite).append("\",");
        sb.append("\"x\": ").append(x).append(",");
        sb.append("\"y\": ").append(y).append(",");
        sb.append("\"pieces\": [");
        for (GamePiece p : pieces) {
            sb.append(p.toJson()).append(",");
        }
        if (!pieces.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
        }
        sb.append("]");
        sb.append("}");

        return sb.toString();
    }

    public GamePiece getPieceByPlayer(String playerId) {
        for (GamePiece piece : pieces) {
            if (piece.getOwner().getId().equals(playerId)) {
                return piece;
            }
        }
        return null;
    }
}
