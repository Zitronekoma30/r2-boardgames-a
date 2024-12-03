package chessdemo;

import com.boardgame.core.GameManager;
import com.boardgame.core.GameBoard;
import com.boardgame.core.Player;

public class App {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(2, 2); // instantiate empty 8x8 chess board

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new ChessTile(x, y, board));
            }
        }

        GameManager gm = GameManager.getInstance();
        gm.setBoard(board);

        var white = new Player();
        var black = new Player();
        gm.addPlayer(white);
        gm.addPlayer(black);

        var pawn1 = new Pawn(true);
        white.addPiece(pawn1);

        board.placePiece(0, 1, pawn1);

        GameManager.getInstance().startGame();
        GameManager.getInstance().printBoardJson();
    }
}

