package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class MoveCountLessMovementRule extends MovementRule{
    private final int moveCount;

    public MoveCountLessMovementRule(MovementRule nextRule, int count){
        super(nextRule);
        moveCount = count;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return piece.getMoves() < moveCount;
    }
}
