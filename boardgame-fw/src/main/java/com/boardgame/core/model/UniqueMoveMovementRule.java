package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class UniqueMoveMovementRule extends MovementRule {
    private GamePiece[] watchedPieces;

    public UniqueMoveMovementRule(MovementRule rule, GamePiece[] watchedPieces){
        super(rule);
        this.watchedPieces = watchedPieces;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        for (GamePiece watchedPiece : watchedPieces){
            if (watchedPiece.checkValidMove(to)) return false;
        }
        return true;
    }

}
