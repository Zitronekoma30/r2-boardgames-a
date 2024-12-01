package com.boardgame.core.model;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class TileTypeBlacklistMovementRule extends MovementRule{
    private final String[] blacklist;

    public TileTypeBlacklistMovementRule(MovementRule nextRule, String[] blacklist){
        super(nextRule);
        this.blacklist = blacklist;
    }

    @Override
    public boolean checkValid(Tile from, Tile to, GamePiece piece) {
        for (String type : blacklist){
            if (to.getClass().getSimpleName().equals(type)){
                return false;
            }
        }
        return true;
    }
}
