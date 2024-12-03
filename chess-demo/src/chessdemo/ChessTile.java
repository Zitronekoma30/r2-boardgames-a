package chessdemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Tile;

public class ChessTile extends Tile {

    public ChessTile(int x, int y, GameBoard board) {
        super(x, y, 2, board); // Fixed cap of 2
        String whiteSprite = "white.png";
        String blackSprite = "black.png";
        setSprite((x+y)%2 == 0 ? whiteSprite : blackSprite); // make even tiles white
    }
}
