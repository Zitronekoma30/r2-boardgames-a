package com.boardgame.core;

import com.boardgame.core.model.move.Move;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance = null;

    private GameBoard activeBoard;
    private List<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;

    public GameManager() {
        players = new ArrayList<>();
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    public GameBoard getBoard() { return activeBoard; }

    public void setBoard(GameBoard board){
        activeBoard = board;
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setGameManager(this);
    }

    public Player getPlayerById(String id) {
        for (Player p : players){
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public void startGame(){
        currentPlayer = players.getFirst();
        gameStarted = true;
        // TODO: implement generic game start logic
    }

    public void printBoardJson(){
        System.out.println(activeBoard.toJson());
    }

    public boolean executeMove(Move move) {
        if (move.getPlayer() != currentPlayer) {
            System.out.println("Move failed, not this players turn");
            return false;
        }
        boolean result = move.execute();
        if (result) {
            passTurn();
        }
        return result;
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
        System.out.println("New active player: " + currentPlayer.getName());
    }

    public void gameEnd (){
        gameStarted = false;
    }
    // TODO: Add turn passing functionality

}
