package com.boardgame.core.model.hand;

import com.boardgame.core.GameBoard;

public abstract class PlayValidator {
    public abstract boolean validate(Action[] actions, GameBoard board);
}
