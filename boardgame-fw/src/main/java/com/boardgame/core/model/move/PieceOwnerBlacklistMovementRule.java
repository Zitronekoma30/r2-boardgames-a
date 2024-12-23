package com.boardgame.core.model.move;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.Tile;

public class PieceOwnerBlacklistMovementRule extends DiscreteMovementRule {
    private final Player owner;

    public PieceOwnerBlacklistMovementRule(MovementRule nextRule, Player owner) {
        super(nextRule);
        this.owner = owner;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        return !to.hasPieceByPlayer(owner);
    }
}
