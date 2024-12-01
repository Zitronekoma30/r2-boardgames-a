package com.boardgame.core.model;

import com.boardgame.core.Tile;
import java.util.ArrayList;

public class PositionMovementRule extends MovementRule{
    private ArrayList<int[]> validPositions;

    public PositionMovementRule(ArrayList<int[]> validPositions){
        this.validPositions = validPositions;
    }

    public boolean isValidMove(Tile from, Tile to){
        int[] currentPosition = {from.getX(), from.getY()};
        int[] targetPosition = {to.getX(), to.getY()};
        int[] move = {targetPosition[0] - currentPosition[0], targetPosition[1] - currentPosition[1]};

        for (int[] validPosition : validPositions){
            if (move[0] == validPosition[0] && move[1] == validPosition[1]){
                return true;
            }
        }

        return true;
    }
}
