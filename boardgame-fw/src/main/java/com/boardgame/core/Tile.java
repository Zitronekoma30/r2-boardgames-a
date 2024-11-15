package com.boardgame.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {
    public final String tileName = "tile";
    private final int pieceCapacity;
    private final List<GamePiece> pieces = new ArrayList<>();
    private final int x;
    private final int y;

    private String sprite;

    public Tile(int x, int y, int pieceCapacity) {
        this.x = x;
        this.y = y;
        this.pieceCapacity = pieceCapacity;
    }

    public boolean addPiece(GamePiece piece) {
        if (pieces.size() >= pieceCapacity) {
            return false;
        }
        onPieceEnter(piece);
        pieces.add(piece);
        return true;
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

    public void onPieceEnter(GamePiece piece) {} // new piece not yet in list
    public void onPieceExit(GamePiece piece) {} // piece not yet removed from list
}
