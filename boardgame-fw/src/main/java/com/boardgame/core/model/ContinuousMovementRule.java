package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class ContinuousMovementRule extends MovementRule { // TODO: Decide whether enough of this can be generalized to have this be abstract
    private int[] direction;
    private int range;

    public ContinuousMovementRule(MovementRule nextRule, int[] direction, int range){
        super(nextRule);
        this.direction = direction;
        this.range = range;
    }

    @Override
    public boolean isValidMove(Tile from, Tile to, GamePiece piece) {
        return false; // TODO: Implement this
    }

    @Override
    protected boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return false;
    }
}
