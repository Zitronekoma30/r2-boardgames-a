package com.boardgame.core;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance = null;

    private GameBoard activeBoard;
    private List<Player> players;
    private Player currentPlayer;

    private Map<String, Class<? extends GamePiece>> pieceTypes;
    private Map<String, Class<? extends Tile>> tileTypes;

    private GameManager() {
        players = new ArrayList<Player>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    public void setBoard(GameBoard board){
        activeBoard = board;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void startGame(){
        currentPlayer = players.getFirst();
        // TODO: implement generic game start logic
    }

    // TODO: Add turn passing functionality

}
