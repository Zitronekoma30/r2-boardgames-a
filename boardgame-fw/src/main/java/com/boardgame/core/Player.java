package com.boardgame.core;

import com.boardgame.core.model.hand.Action;
import com.boardgame.model.hand.handPlayValidator;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    static int pnum = 0;

    private GameManager gm;
    private ArrayList<GamePiece> ownedPieces;
    private String name;
    private String id;
    private int[] directions;

    private ArrayList<GamePiece> hand;
    private handPlayValidator handPlayValidator;

    public Player(String name, int[] directions){
        ownedPieces = new ArrayList<>();
        this.name = name;
        this.directions = directions;
        this.hand = new ArrayList<>();
    }

    public void setGameManager(GameManager gm) {
        this.gm = gm;
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

    public GamePiece getPieceFromHand(int i) {
        return hand.get(i);
    }

    public void Win(){
        gm.gameEnd();
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

    public boolean validateHandPlay(Action[] actions, GameBoard board) {
        return handPlayValidator.validate(actions, board);
    }

    public void setHandPlayValidator(handPlayValidator handPlayValidator) {
        this.handPlayValidator = handPlayValidator;
    }


}
