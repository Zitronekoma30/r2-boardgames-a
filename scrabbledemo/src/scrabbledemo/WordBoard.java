package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WordBoard extends GameBoard {
    private HashSet<LetterTile> allowedTiles;
    private ScrabbleWordValidator wordValidator;

    public WordBoard(int width, int height, String wordlistPath) {
        super(width, height);
        this.allowedTiles = new HashSet<>();
        try {
            this.wordValidator = new ScrabbleWordValidator(wordlistPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean handleHandPlay(Player player){
        // 1. Find all words
        var words = findAllWords(player);

        // 2. filter for new
        words = words.stream().filter(Word::containsNew).toList();

        // 3. check if all new chars are in word TODO: keep track of all new chars earlier so this will be less expensive
        if (false) { // TODO: implement this
            rollback(player);
            return false;
        }

        // 4. Get Score
        var score = words.stream().mapToInt(Word::getScore).sum();

        // 5. remove used tiles from allowed
        for (Word word: words){
            for (LetterTile tile : word.getTiles()){
                allowedTiles.remove(tile);
            }
        }

        // 6. Get all empty adjacent tiles of new tiles and add them to allowed positions
        for (Word word : words) {
            for (LetterTile tile : word.getTiles()) {
                addAdjacentEmptyTiles(tile);
            }
        }

        // 7. Unmark letters as new, return true
        for (Word word : words) {
            for (LetterTile tile : word.getTiles()) {
                tile.getPiece().setNewlyPlaced(false);
            }
        }

        // Add score
        var p = (ScrabblePlayer) player;
        p.addScore(score);

        return true;
    }

    public List<Word> findAllWords(Player player) {
        List<Word> words = new ArrayList<>();
        boolean[][] visited = new boolean[getWidth()][getHeight()];

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                LetterTile tile = (LetterTile) getTile(x, y);
                if (tile.getPieceByPlayer(player) != null && !visited[x][y]) {
                    Word word = new Word();
                    dfs(tile, player, visited, word);
                    if (wordValidator.isValidWord(word.toString())) {
                        words.add(word);
                    }
                }
            }
        }
        return words;
    }

    private void dfs(LetterTile tile, Player player, boolean[][] visited, Word word) {
        int x = tile.getX();
        int y = tile.getY();
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight() || visited[x][y]) {
            return;
        }

        visited[x][y] = true;
        LetterPiece piece = (LetterPiece) tile.getPieceByPlayer(player);
        if (piece != null) {
            word.addTile(tile);
            word.addScore(piece.getScore());

            // Explore neighbors
            dfs((LetterTile) getTile(x + 1, y), player, visited, word);
            dfs((LetterTile) getTile(x - 1, y), player, visited, word);
            dfs((LetterTile) getTile(x, y + 1), player, visited, word);
            dfs((LetterTile) getTile(x, y - 1), player, visited, word);
        }
    }

    public void setMiddle(){
        var middle = (LetterTile) getTile(getWidth()/2, getHeight()/2);
        middle.setMiddle();
        allowedTiles.add(middle);
    }

    public HashSet<LetterTile> getAllowedTiles(){
        return allowedTiles;
    }

    private void rollback(Player player) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                LetterTile tile = (LetterTile) getTile(x, y);
                LetterPiece piece = (LetterPiece) tile.getPieceByPlayer(player);
                if (piece != null && piece.getNewlyPlaced()) {
                    player.addPieceToHand(piece);
                    tile.removePiece(piece);
                    piece.setNewlyPlaced(false);
                }
            }
        }
    }

    private void addAdjacentEmptyTiles(LetterTile tile) {
        int x = tile.getX();
        int y = tile.getY();
        addIfEmpty(x + 1, y);
        addIfEmpty(x - 1, y);
        addIfEmpty(x, y + 1);
        addIfEmpty(x, y - 1);
    }

    private void addIfEmpty(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            LetterTile tile = (LetterTile) getTile(x, y);
            if (tile.getPiece() == null) {
                allowedTiles.add(tile);
            }
        }
    }
}
