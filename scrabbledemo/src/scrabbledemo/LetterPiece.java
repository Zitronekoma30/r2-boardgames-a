package scrabbledemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;

public class LetterPiece extends GamePiece {
    private Player player;
    private Character letter;
    private boolean newlyPlaced;

    public LetterPiece(Player player, Character letter){
        this.player = player;
        this.letter = letter;
        this.newlyPlaced = false;

        setSprite("letter_"+letter.toString().toUpperCase()+".png");
    }

    public boolean getNewlyPlaced() {
        return newlyPlaced;
    }

    public void setNewlyPlaced(boolean newlyPlaced){
        this.newlyPlaced = newlyPlaced;
    }
}
