package com.boardgame.core;

public class GameBoard {
    private Tile[][] tiles;

    public GameBoard(int width, int height) {
        this.tiles = new Tile[width][height];
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public int getWidth() {return tiles[0].length; }
    public int getHeight() {return tiles.length; }

    public boolean placePiece(int x, int y, GamePiece piece){
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) return false;
        return tiles[x][y].addPiece(piece);
    }

    protected Tile[][] getArray(){
        return tiles;
    }

}
