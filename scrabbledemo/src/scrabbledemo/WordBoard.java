package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Player;

public class WordBoard extends GameBoard {
    public WordBoard(int width, int height) {
        super(width, height);
    }

    public boolean handleHandPlay(Player player){
        /* TODO:
            1. Find all words on board
            2. Filter only those containing new characters
            3. Check if all new characters are in a word, if not -> roll back
            4. Calculate score for new words and add score to player
            5. Unmark letters as new, return true
            roll-back:
                1. Go over every piece on the board
                if new -> add to player hand and remove from board then unmark as new
                if not new -> ignore
                2. return false
         */
        return true;
    }
}
