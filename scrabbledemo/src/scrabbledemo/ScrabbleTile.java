package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.GameManager;
import com.boardgame.core.Tile;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

public class ScrabbleTile extends Tile {
    public ScrabbleTile(int x, int y, GameBoard board) {
        super(x, y, 1, board);
    }
}
