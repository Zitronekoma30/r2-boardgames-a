package com.boardgame.core;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    static int pnum = 0;

    private ArrayList<GamePiece> ownedPieces;
    private String name;
    private String id;
    private int[] directions;

    private ArrayList<GamePiece> hand;

    public Player(String name, int[] directions){
        ownedPieces = new ArrayList<>();
        this.name = name;
        this.directions = directions;
        this.hand = new ArrayList<>();
    }

    public static String generateId() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        return "p" + pnum++ + randomNumber;
    }

    public boolean addPiece(GamePiece piece){
        piece.setOwner(this);
        return ownedPieces.add(piece); // TODO: Flesh this out
    }

    public boolean removePiece(GamePiece piece){
        piece.setOwner(null);
        return ownedPieces.remove(piece);
    }

    public boolean addPieceToHand(GamePiece piece){
        return hand.add(piece);
    }

    public GamePiece removePieceFromHand(int i){
        return hand.remove(i);
    }

    public String[] listPiecesOnHand(){
        return hand.stream().map(p -> p.getClass().getSimpleName()).toArray(String[]::new);
    }

    public String handToJson(){
        StringBuilder out = new StringBuilder();
        out.append("[");

        for (GamePiece p : hand){
            out.append(p.toJson());
            out.append(",");
        }

        if (out.length() > 1) {
            out.deleteCharAt(out.length() - 1); // most browsers don't allow trailing commas in json
        }

        out.append("]");
        return out.toString();
    }

    public void playPieceFromHand(int i, Tile tile){
        var piece = hand.get(i);
        piece.movePiece(tile);
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

    public int[] getDirections() { return directions.clone(); }
}
