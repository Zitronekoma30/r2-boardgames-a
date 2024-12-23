package com.boardgame.core;

import com.boardgame.core.model.move.MovementRule;

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

    protected void setTile(Tile tile){
        this.tile = tile;
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
        if (tile != null) tile.removePiece(this);
        if (!destination.addPiece(this)) return false; // check for tile issues
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

    public boolean checkValidMove(Tile to) {
        /* top level rules are "or" connected: validMove := MR1 || MR2 || MR3 || ...
            * if any rule is true, the piece can move
            * if no rules are true, the piece cannot move
            * Individual rules only return true if all of their sub-rules return true MR1 := MR1_1 && MR1_2 && ...
         */
        if (movementRules == null) return true;
        for (MovementRule rule : movementRules){
            if (rule.isValidMove(tile, to, this)) return true; // check all rules, if any are true piece moves
        }
        return false;
    }
}
