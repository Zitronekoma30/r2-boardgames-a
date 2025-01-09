package com.boardgame.core.model.hand;

import com.boardgame.core.GameBoard;

public class DefaultPlayValidator extends PlayValidator {

    @Override
    public boolean validate(Action[] actions, GameBoard board) {
        return true;
    }
}
