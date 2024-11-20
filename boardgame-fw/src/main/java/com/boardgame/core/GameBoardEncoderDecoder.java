package com.boardgame.core;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameBoardEncoderDecoder {
    public String encodeBoard(GameBoard board){
        JSONArray boardJson = new JSONArray();

        for (Tile[] row : board.getArray()) {
            JSONArray rowJson = new JSONArray();
            for (Tile tile : row) {
                rowJson.put(tile == null ? JSONObject.NULL : new JSONObject(tile.toJson()));
            }
            boardJson.put(rowJson);
        }

        return boardJson.toString();
    }
}
