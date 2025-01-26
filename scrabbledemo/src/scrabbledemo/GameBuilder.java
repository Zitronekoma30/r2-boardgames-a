package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.GameManager;
import com.boardgame.core.GameManagerFactory;
import com.boardgame.core.Player;

public class GameBuilder implements GameManagerFactory {

    @Override
    public GameManager produceGameManager() {
        // dictionary source: https://github.com/redbo/scrabble/blob/master/dictionary.txt
        WordBoard board = new WordBoard(15, 15, "src/resources/dictionary.txt");

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new LetterTile(x, y, board));
            }
        }

        board.setMiddle();

        GameManager gm = new GameManager(2);
        gm.setBoard(board);

        LetterPouch pouch = new LetterPouch();

        var p1 = new ScrabblePlayer("1", new int[]{1, 1}, pouch, 7);
        var p2 = new ScrabblePlayer("2", new int[]{1, 1}, pouch, 7);

        p1.setHandPlayValidator(new LetterPlayValidator());
        p2.setHandPlayValidator(new LetterPlayValidator());

        // var p3 = new Player("3", new int[]{1, 1});
        // var p4 = new Player("4", new int[]{1, 1});

        gm.addPlayer(p1);
        gm.addPlayer(p2);

        // board.placePiece(4,4, new LetterPiece(p1, 'F'));

        p1.addPieceToHand(new LetterPiece(p1, 'A'));
        p1.addPieceToHand(new LetterPiece(p1, 'L'));
        p1.addPieceToHand(new LetterPiece(p1, 'L'));

        p2.addPieceToHand(new LetterPiece(p2, 'T'));
        p2.addPieceToHand(new LetterPiece(p2, 'R'));
        p2.addPieceToHand(new LetterPiece(p2, 'E'));
        p2.addPieceToHand(new LetterPiece(p2, 'E'));

        // Add Events
        var getScore = new GetScoreEvent("get-score");
        var getLetters = new GetLettersEvent("get-letters");

        return gm;


        /*
        GameManager man = new GameManager(2);
        GameBoard b = new GameBoard(1,1);
        man.setBoard(b);

        man.addPlayer(new Player("p1", new int[] {1,1}));
        man.addPlayer(new Player("p2", new int[] {1,1}));

        return man;
        */
    }
}
