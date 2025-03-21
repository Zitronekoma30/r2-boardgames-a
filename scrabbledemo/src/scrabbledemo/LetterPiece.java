package scrabbledemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;

import java.util.HashMap;

public class LetterPiece extends GamePiece {
    private Player player;
    private Character letter;
    private boolean newlyPlaced;
    private int score = 0;

    private static HashMap<Character, Integer> scores = new HashMap<Character, Integer>();

    static {
        scores.put('A', 1);
        scores.put('B', 3);
        scores.put('C', 3);
        scores.put('D', 2);
        scores.put('E', 1);
        scores.put('F', 4);
        scores.put('G', 2);
        scores.put('H', 4);
        scores.put('I', 1);
        scores.put('J', 8);
        scores.put('K', 5);
        scores.put('L', 1);
        scores.put('M', 3);
        scores.put('N', 1);
        scores.put('O', 1);
        scores.put('P', 3);
        scores.put('Q', 10);
        scores.put('R', 1);
        scores.put('S', 1);
        scores.put('T', 1);
        scores.put('U', 1);
        scores.put('V', 4);
        scores.put('W', 4);
        scores.put('X', 8);
        scores.put('Y', 4);
        scores.put('Z', 10);
    }

    public LetterPiece(Player player, Character letter){
        this.player = player;
        this.letter = letter;
        this.newlyPlaced = false;
        this.score = scores.get(letter);

        setSprite("letter_"+letter.toString().toUpperCase()+".png");
    }

    public int getScore(){
        var tile = (LetterTile) getTile();
        return score * tile.getScoreMult();
    }

    public Character getLetter(){
        return letter;
    }

    public boolean getNewlyPlaced() {
        return newlyPlaced;
    }

    public void setNewlyPlaced(boolean newlyPlaced){
        this.newlyPlaced = newlyPlaced;
    }
}
