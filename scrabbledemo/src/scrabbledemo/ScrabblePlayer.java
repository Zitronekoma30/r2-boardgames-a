package scrabbledemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;

import java.util.List;

public class ScrabblePlayer extends Player {
    private int score = 0;
    private int letterCapacity;
    private LetterPouch pouch;

    public ScrabblePlayer(String name, int[] directions, LetterPouch pouch, int capacity) {
        super(name, directions);
        this.pouch = pouch;
        this.letterCapacity = capacity;
    }

    public int getScore() {
        return score;
    }

    public int addScore(int amount){
        score += amount;
        return score;
    }

    public void replaceLetters(List<Character> letters) {
        var hand = getHand();
        int newAmount = 0;

        for (Character letter : letters){
            for (GamePiece piece : hand){
                if (((LetterPiece) piece).getLetter() == letter){
                    removePiece(piece);
                    newAmount++;
                    break;
                }
            }
        }

        addLetters(newAmount);
    }

    public void fillLetters(){
        int numToAdd = letterCapacity - getHand().length;
        addLetters(numToAdd);
    }

    public void replaceLetterAt(int position){
        removePieceFromHand(position);
        addPieceToHand(pouch.getNextLetter(this));
    }

    private void addLetters(int amount) {
        for (int i = 0; i < amount; i++) {
            addPieceToHand(pouch.getNextLetter(this));
        }
    }
}
