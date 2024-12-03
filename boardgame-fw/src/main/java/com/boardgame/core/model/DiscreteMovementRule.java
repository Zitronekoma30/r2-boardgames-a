package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public abstract class DiscreteMovementRule extends MovementRule {

    public DiscreteMovementRule(MovementRule nextRule){
        super(nextRule);
    }

    @Override
    public boolean isValidMove(Tile from, Tile to, GamePiece piece) {
        boolean valid;
        valid = checkValid(from, to, piece);

        if (super.getNext() != null && valid){
            return super.getNext().isValidMove(from, to, piece);
        }
        return valid;
    }

    @Override
    public abstract boolean checkValid(Tile from, Tile to, GamePiece piece);
}
