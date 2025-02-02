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

        // Add Events
        var getScore = new GetScoreEvent("get-score");
        var getLetters = new GetLettersEvent("get-letters");

        gm.addEvent(getScore);
        gm.addEvent(getLetters);

        p1.fillLetters();
        p2.fillLetters();

        return gm;
    }
}
