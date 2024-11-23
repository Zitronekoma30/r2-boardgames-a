package com.boardgame.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameBoard {
    private Tile[][] tiles;

    public GameBoard(int width, int height) {
        this.tiles = new Tile[width][height];
    }

    public Tile getTile(int x, int y){
        try {
            return tiles[x][y];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid tile requested at: (" + x + ", " + y + ")");
            return null;
        }
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

    public String toJson() {
        JSONArray boardJson = new JSONArray();

        for (Tile[] row : tiles) {
            JSONArray rowJson = new JSONArray();
            for (Tile tile : row) {
                rowJson.put(tile == null ? JSONObject.NULL : new JSONObject(tile.toJson()));
            }
            boardJson.put(rowJson);
        }

        return boardJson.toString();
    }

}
