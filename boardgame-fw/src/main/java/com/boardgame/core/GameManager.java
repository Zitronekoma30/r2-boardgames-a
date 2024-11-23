package com.boardgame.core;

import com.boardgame.core.model.Move;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance = null;

    private GameBoard activeBoard;
    private GameServer server;
    private List<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;

    private GameManager() {
        players = new ArrayList<>();
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
        String frontEndPath = new File("src/view").getAbsolutePath();
        server = new GameServer(this, activeBoard, "localhost", 8080, frontEndPath);
        try {
            server.startServer();
        } catch (Exception e) {
            System.out.println("Failed to start server due to exception: " + e);
        }
        gameStarted = true;
        // TODO: implement generic game start logic
    }

    public void printBoardJson(){
        System.out.println(activeBoard.toJson());
    }

    public boolean executeMove(Move move) {
        if (move.getPlayer() != currentPlayer) {
            return false;
        }

        return move.execute();
    }

    public String joinGame() {
        for (Player player : players) {
            if (player.getId() == null) {
                String id = Player.generateId();
                player.setId(id);
                return id;
            }
        }
        return "failed";
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
