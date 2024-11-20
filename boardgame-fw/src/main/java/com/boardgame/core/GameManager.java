package com.boardgame.core;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance = null;

    private GameBoard activeBoard;
    private GameServer server;
    private GameBoardEncoderDecoder boardEncDec;
    private List<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;

    private GameManager() {
        players = new ArrayList<>();
        boardEncDec = new GameBoardEncoderDecoder();
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
        currentPlayer = players.get(0);
        gameStarted=true;
        // TODO: implement generic game start logic
    }

    public void printBoardJson(){
        System.out.println(boardEncDec.encodeBoard(activeBoard));
    }

    public void passTurn(){
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayer = (currentPlayerIndex + 1) % players.size();  
        currentPlayer = players.get(nextPlayer);
    }

    public void gameEnd (){
        gameStarted = false;
    }
    // TODO: Add turn passing functionality

}
