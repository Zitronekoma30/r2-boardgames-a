import com.boardgame.core.GameManager;
import com.boardgame.core.GameBoard;
import com.boardgame.core.Player;

public class App {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(8, 8); // instantiate empty 8x8 chess board

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new ChessTile(x, y));
            }
        }

        GameManager gm = GameManager.getInstance();
        gm.setBoard(board);

        var p1 = new Player();
        var p2 = new Player();
        gm.addPlayer(p1);
        gm.addPlayer(p2);

        var pawn1 = new Pawn();
        p1.addPiece(pawn1);
        board.placePiece(0, 1, pawn1);

        GameManager.getInstance().startGame();
    }
}

