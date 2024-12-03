package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class RelativePositionMovementRule extends MovementRule{
    private final int[][] validPositions;

    public RelativePositionMovementRule(MovementRule nextRule, int[][] validPositions){
        super(nextRule);
        this.validPositions = validPositions;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece){
        int[] currentPosition = {from.getX(), from.getY()};
        int[] targetPosition = {to.getX(), to.getY()};
        int[] move = {targetPosition[0] - currentPosition[0], targetPosition[1] - currentPosition[1]};

        for (int[] validPosition : validPositions){
            if (move[0] == validPosition[0] && move[1] == validPosition[1]){
                return true;
            }
        }

        return false;
    }
}
