package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Player;

import java.util.HashSet;

public class WordBoard extends GameBoard {
    private HashSet<LetterTile> allowedTiles;

    public WordBoard(int width, int height) {
        super(width, height);
        this.allowedTiles = new HashSet<>();
    }

    public boolean handleHandPlay(Player player){
        /* TODO:
            1. Find all words on board
            2. Filter only those containing new characters
            3. Check if all new characters are in a word, if not -> roll back
            4. Calculate score for new words and add score to player
            5. Remove all used position from allowed positions
            6. Get all empty adjacent tiles of new tiles and add them to allowed positions
            7. Unmark letters as new, return true
            roll-back:
                1. Go over every piece on the board
                if new -> add to player hand and remove from board then unmark as new
                if not new -> ignore
                2. return false
         */
        return true;
    }

    public void setMiddle(){
        var middle = (LetterTile) getTile(getWidth()/2, getHeight()/2);
        middle.setMiddle();
        allowedTiles.add(middle);
    }

    public HashSet<LetterTile> getAllowedTiles(){
        return allowedTiles;
    }
}
