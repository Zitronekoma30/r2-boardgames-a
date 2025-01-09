package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.GameManager;
import com.boardgame.core.GameManagerFactory;
import com.boardgame.core.Player;

public class GameBuilder implements GameManagerFactory {
    @Override
    public GameManager produceGameManager() {
        GameBoard board = new GameBoard(15, 14);

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new LetterTile(x, y, board));
            }
        }

        GameManager gm = new GameManager(2);
        gm.setBoard(board);

        var p1 = new Player("1", new int[]{1, 1});
        var p2 = new Player("2", new int[]{1, 1});
        // var p3 = new Player("3", new int[]{1, 1});
        // var p4 = new Player("4", new int[]{1, 1});

        return gm;
    }
}
