package com.boardgame.core;

import java.util.Map;

// TODO: Figure this out down the line / scrap the idea

public class GameObjectRegistry {
    private Map<String, Class<? extends GamePiece>> pieceTypes;
    private Map<String, Class<? extends Tile>> tileTypes;

    public void registerTile(Class<? extends Tile> tileClass){

    }

    public void registerGamePiece(Class<? extends Tile> pieceClass){

    }
}
