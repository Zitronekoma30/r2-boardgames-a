package com.boardgame.core;

import java.util.ArrayList;

public class Player {
    static int pnum = 0;

    private ArrayList<GamePiece> ownedPieces;
    private String name;

    public Player(){
        ownedPieces = new ArrayList<GamePiece>();
        name = "p" + pnum;
        pnum++;
    }

    public boolean addPiece(GamePiece piece){
        piece.setOwner(this);
        return ownedPieces.add(piece); // TODO: Flesh this out
    }

    public boolean removePiece(GamePiece piece){
        return ownedPieces.remove(piece);
    }

    public void Win(){
        // TODO: call GameManager or something
    }

    public String getName() {
        return name;
    }
}
