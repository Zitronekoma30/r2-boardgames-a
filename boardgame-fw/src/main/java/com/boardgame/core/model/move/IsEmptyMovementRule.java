package com.boardgame.core.model.move;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class IsEmptyMovementRule extends DiscreteMovementRule{

    public IsEmptyMovementRule(MovementRule nextRule) {
        super(nextRule);
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return to.isEmpty();
    }
}
