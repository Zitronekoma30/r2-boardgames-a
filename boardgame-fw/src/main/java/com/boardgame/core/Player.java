package com.boardgame.core;

import java.util.ArrayList;

public class Player {
    private ArrayList<GamePiece> ownedPieces;

    public Player(){
        ownedPieces = new ArrayList<GamePiece>();
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
}
