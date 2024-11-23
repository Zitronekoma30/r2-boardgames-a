package com.boardgame.core;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    static int pnum = 0;

    private ArrayList<GamePiece> ownedPieces;
    private String name;
    private String id;

    public Player(){
        ownedPieces = new ArrayList<GamePiece>();
        name = "p" + pnum;
        pnum++;
    }

    public static String generateId() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        return "p" + pnum + randomNumber;
    }

    public boolean addPiece(GamePiece piece){
        piece.setOwner(this);
        return ownedPieces.add(piece); // TODO: Flesh this out
    }

    public boolean removePiece(GamePiece piece){
        piece.setOwner(null);
        return ownedPieces.remove(piece);
    }

    public void Win(){
        GameManager.getInstance().gameEnd();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
