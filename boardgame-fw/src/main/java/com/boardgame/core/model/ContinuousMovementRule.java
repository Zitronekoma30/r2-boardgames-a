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
        int x = from.getX();
        int y = from.getY();

        int stepX = direction[0];
        int stepY = direction[1];

        // Iterate through the path in the given direction
        for (int steps = 1; steps <= range; steps++) {
            x += stepX;
            y += stepY;

            Tile currentTile = from.getBoard().getTile(x, y);
            if (currentTile == null) {
                // out of bounds
                return false; // Entire rule chain fails
            }

            if (currentTile.equals(to)) {
                // Destination reached; check next rule in the chain
                return true;
            }

            if (super.getNext() != null && !super.getNext().isValidMove(from, currentTile, piece)) {
                return false; // Next rule in the chain fails
            }
        }
        return false; // Destination not reached within range
    }

    @Override
    protected boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return false;
    }
}
