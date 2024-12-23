package com.boardgame.core.model.move;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

import java.util.Arrays;

public class PieceBlacklistMovementRule extends DiscreteMovementRule {
    private final String[] pieceNames;

    public PieceBlacklistMovementRule(MovementRule nextRule, Class[] pieceTypes) {
        super(nextRule);
        pieceNames = Arrays.stream(pieceTypes).map(Class::getSimpleName).toArray(String[]::new);
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        for (String p : pieceNames){
            if (to.hasPieceOfType(p)) return false;
        }
        return true;
    }
}
