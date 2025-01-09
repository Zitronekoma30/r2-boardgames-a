package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.model.hand.Action;
import com.boardgame.core.model.hand.PlayValidator;

public class LetterPlayValidator extends PlayValidator {

    @Override
    public boolean validate(Action[] actions, GameBoard gameBoard) {
        // TODO: Check if all destinations are adjacent (non diagonal)
        // TODO: Mark all pieces as "new" so the board can recognize them and roll back if needed
        return false;
    }
}
