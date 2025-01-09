package scrabbledemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;

public class LetterPiece extends GamePiece {
    private Player player;
    private Character letter;

    public LetterPiece(Player player, Character letter){
        this.player = player;
        this.letter = letter;

        setSprite("letter_"+letter.toString().toUpperCase()+".png");
    }
}
