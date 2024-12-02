package com.boardgame.core;

import com.boardgame.core.model.MovementRule;

import java.util.ArrayList;

public abstract class GamePiece {
    public static final String pieceName = "piece";

    private Tile tile;
    private Player owner;
    private String sprite;

    private int moves = 0;

    private ArrayList<MovementRule> movementRules;

    protected void setOwner(Player player){ owner = player; }
    public Player getOwner(){ return owner; }

    public int getMoves(){ return moves; }

    public Tile getTile(){
        return tile;
    }

    public void setSprite(String sprite){
        this.sprite = sprite;
    }

    protected void addMovementRule(MovementRule rule){
        if (movementRules == null) movementRules = new ArrayList<>();
        movementRules.add(rule);
    }

    public boolean movePiece(Tile destination){
        if (!checkValidMove(destination)) return false; // check for piece movement rule violation
        if (!destination.addPiece(this)) return false; // check for tile issues
        if (tile != null) tile.removePiece(this);

        tile = destination;
        moves++;

        return true;
    }

    public String toJson() {
        return "{" +
                "\"pieceName\": \"" + this.getClass().getSimpleName() + "\"," +
                "\"sprite\": \"" + sprite + "\"," +
                "\"player\": \"" + (owner != null ? owner.getName() : "null") + "\"" +
                "}";
    }

    private boolean checkValidMove(Tile to) {
        if (movementRules == null) return true;
        for (MovementRule rule : movementRules){
            if (rule.checkValid(tile, to, this)) return true; // check all rules, if any are true piece moves
        }
        return false;
    }
}
