package com.boardgame.core.model.hand;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.Tile;

public record Action(int handIdx, GamePiece piece, Player player, Tile to) {
}
