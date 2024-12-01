package com.boardgame.core.model;

import com.boardgame.core.Tile;

public abstract class MovementRule {
    public abstract boolean isValidMove(Tile from, Tile to);

    public boolean isValidMove(Move move){
        return isValidMove(move.getFrom(), move.getTo());
    }
}
