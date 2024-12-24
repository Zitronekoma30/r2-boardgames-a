package com.boardgame.core.model.move;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class InvertDiscreteMovementRule extends DiscreteMovementRule{
    private final MovementRule inverted;

    public InvertDiscreteMovementRule(MovementRule nextRule, MovementRule inverted) {
        super(nextRule);
        this.inverted = inverted;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return !inverted.isValidMove(from, to, piece);
    }
}
