package com.boardgame.core;

public abstract class GamePiece {
    public static final String pieceName = "piece";

    private Tile tile;
    private Player owner;
    private String sprite;

    protected void setOwner(Player player){ owner = player; }
    public Player getOwner(){ return owner; }

    public void setSprite(String sprite){
        this.sprite = sprite;
    }

    public boolean movePiece(Tile destination){
        if (!destination.addPiece(this)) return false;
        tile = destination;
        return true;
    }

    public String toJson() {
        return "{" +
                "\"pieceName\": \"" + this.getClass().getSimpleName() + "\"," +
                "\"sprite\": \"" + sprite + "\"," +
                "\"player\": \"" + (owner != null ? owner.getName() : "null") + "\"" +
                "}";
    }
}
