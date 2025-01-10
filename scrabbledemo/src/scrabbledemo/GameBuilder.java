package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.GameManager;
import com.boardgame.core.GameManagerFactory;
import com.boardgame.core.Player;

public class GameBuilder implements GameManagerFactory {
    @Override
    public GameManager produceGameManager() {
        WordBoard board = new WordBoard(15, 15);

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new LetterTile(x, y, board));
            }
        }

        board.setMiddle();

        GameManager gm = new GameManager(2);
        gm.setBoard(board);

        var p1 = new Player("1", new int[]{1, 1});
        var p2 = new Player("2", new int[]{1, 1});

        p1.setHandPlayValidator(new LetterPlayValidator());
        p2.setHandPlayValidator(new LetterPlayValidator());
        // var p3 = new Player("3", new int[]{1, 1});
        // var p4 = new Player("4", new int[]{1, 1});

        gm.addPlayer(p1);
        gm.addPlayer(p2);

        board.placePiece(4,4, new LetterPiece(p1, 'F'));

        p1.addPieceToHand(new LetterPiece(p1, 'A'));
        p1.addPieceToHand(new LetterPiece(p1, 'L'));
        p1.addPieceToHand(new LetterPiece(p1, 'L'));

        return gm;
    }
}
