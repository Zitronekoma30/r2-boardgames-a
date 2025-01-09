package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Tile;

public class LetterTile extends Tile {
    public LetterTile(int x, int y, GameBoard board) {
        super(x, y, 1, board);
        String sprite = "tile_" + (((x+y) % 2 == 0) ? "black" : "white") + ".png";
        setSprite(sprite);
    }
}