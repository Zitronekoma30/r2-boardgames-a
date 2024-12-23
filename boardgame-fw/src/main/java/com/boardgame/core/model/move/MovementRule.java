package com.boardgame.core.model.move;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public abstract class MovementRule {
    private MovementRule nextRule;

    public MovementRule(MovementRule nextRule){
        this.nextRule = nextRule;
    }

    public abstract boolean isValidMove(Tile from, Tile to, GamePiece piece);

    public final boolean isValidMove(Move move){
        return isValidMove(move.getFrom(), move.getTo(), move.getPiece());
    }

    public MovementRule getNext(){
        return nextRule;
    }

    protected abstract boolean checkValid(Tile from, Tile to, GamePiece piece);
}
