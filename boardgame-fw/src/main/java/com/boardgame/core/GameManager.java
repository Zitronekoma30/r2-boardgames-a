package com.boardgame.core;

import com.boardgame.core.model.event.Event;
import com.boardgame.core.model.hand.HandPlay;
import com.boardgame.core.model.move.Move;

import java.util.List;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance = null;

    private GameBoard activeBoard;
    private List<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;
    private ArrayList<String> serverContexts;
    private ArrayList<Event> events;
    private int playerCapacity;

    public GameManager(int playerCapacity) {
        this.events = new ArrayList<>();
        this.playerCapacity = playerCapacity;
        serverContexts = new ArrayList<>();
        players = new ArrayList<>();
    }

    public String addServerContext(String path){
        serverContexts.add(path);
        return path;
    }

    public void addEvent(Event event){
        events.add(event);
        event.setManager(this);
    }

    public Event[] getEvents(){
        return events.toArray(Event[]::new);
    }

    public String[] getServerContextPaths(){
        return serverContexts.toArray(String[]::new);
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

    public boolean executeHandPlay(HandPlay play) {
        if (!play.isValid() || play.getPlayer() != currentPlayer){
            System.out.println("Play from hand failed at stage 1");
            return false;
        }

        // actually place tiles on the board
        play.execute();

        /* board validates play and passes turn if so, otherwise it takes care of rollback.
           This is usually expensive so by default it just passes, but it allows for more complex board wide
           pattern matching as a validation condition.*/
        if (!activeBoard.handleHandPlay(currentPlayer)) {
            System.out.println("Play from hand failed at stage 2");
            return false;
        }

        System.out.println("Play from hand passed");

        passTurn();
        return true;
    }

    public String joinGame() {
        String returnVal = "failed";
        for (Player player : players) {
            if (player.getId() == null) {
                String id = Player.generateId();
                player.setId(id);
                returnVal = id;
                break;
            }
        }

        if (players.stream().noneMatch(p -> p.getId() == null)) startGame();

        return returnVal;
    }

    public void passTurn(){
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(nextPlayerIndex);
        System.out.println("New active player: " + currentPlayer.getName());
    }

    public void gameEnd (){
        gameStarted = false;
    }

    public boolean isFull() {
        for (int i = 0; i < playerCapacity; i++) {
            if (players.get(i).getId() == null) return false;
        }
        return true;
    }

}
