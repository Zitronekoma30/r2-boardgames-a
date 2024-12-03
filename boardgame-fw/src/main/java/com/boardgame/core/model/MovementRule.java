package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public abstract class MovementRule {
    private MovementRule nextRule;

    public MovementRule(MovementRule nextRule){
        this.nextRule = nextRule;
    }

    public final boolean isValidMove(Tile from, Tile to, GamePiece piece){
        boolean valid;
        valid = checkValid(from, to, piece);

        if (nextRule != null && valid){
            return nextRule.isValidMove(from, to, piece);
        }
        return valid;
    }

    public final boolean isValidMove(Move move){
        return isValidMove(move.getFrom(), move.getTo(), move.getPiece());
    }

    protected abstract boolean checkValid(Tile from, Tile to, GamePiece piece);
}
