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
        ((ScrabblePlayer) player).fillLetters();
        return true;
        /*
        System.out.println("Validating...");
        // 1. Find all words
        var words = findAllWords(player);

        System.out.println("Words found");

        // 2. filter for new
        words = words.stream().filter(Word::containsNew).toList();

        System.out.println("Words filtered");

        // 3. check if all new chars are in word TODO: keep track of all new chars earlier so this will be less expensive
        if (false) { // TODO: implement this
            rollback(player);
            return false;
        }

        System.out.println("No stragglers found");

        // 4. Get Score
        var score = words.stream().mapToInt(Word::getScore).sum();

        System.out.println("Score: " + score);

        // 5. remove used tiles from allowed
        for (Word word: words){
            for (LetterTile tile : word.getTiles()){
                allowedTiles.remove(tile);
            }
        }

        System.out.println("Removed allowed");

        // 6. Get all empty adjacent tiles of new tiles and add them to allowed positions
        for (Word word : words) {
            for (LetterTile tile : word.getTiles()) {
                addAdjacentEmptyTiles(tile);
            }
        }

        System.out.println("Add allowed");

        // 7. Unmark letters as new, return true
        for (Word word : words) {
            for (LetterTile tile : word.getTiles()) {
                tile.getPiece().setNewlyPlaced(false);
            }
        }

        System.out.println("Unmarked letters");

        // Add score
        var p = (ScrabblePlayer) player;
        p.addScore(score);
        p.fillLetters();

        System.out.println("Play is Valid");

        return true;
        */
    }

    public List<Word> findAllWords(Player player) {
        List<Word> words = new ArrayList<>();

        // We track which tiles we already processed horizontally/vertically
        HashSet<LetterTile> horizDone = new HashSet<>();
        HashSet<LetterTile> vertDone  = new HashSet<>();

        // Loop over board and find newly placed tiles
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                LetterTile tile = (LetterTile) getTile(x, y);
                LetterPiece piece = (LetterPiece) tile.getPieceByPlayer(player);

                // If this tile has a newly placed piece
                if (piece != null && piece.getNewlyPlaced()) {

                    // Build horizontal word if not done
                    if (!horizDone.contains(tile)) {
                        Word horiz = buildWordHorizontal(x, y);
                        if (horiz != null && horiz.length() > 0 && wordValidator.isValidWord(horiz.toString())) {
                            words.add(horiz);
                            horizDone.addAll(horiz.getTiles());
                        }
                    }

                    // Build vertical word if not done
                    if (!vertDone.contains(tile)) {
                        Word vert = buildWordVertical(x, y);
                        if (vert != null && vert.length() > 0 && wordValidator.isValidWord(vert.toString())) {
                            words.add(vert);
                            vertDone.addAll(vert.getTiles());
                        }
                    }
                }
            }
        }

        return words;
    }

    private Word buildWordHorizontal(int startX, int startY) {
        // Move left to find the start
        int left = startX;
        while (left > 0 && getTile(left - 1, startY).getPieces().getFirst() != null) {
            left--;
        }

        // Move right to find the end
        int right = startX;
        while (right < getWidth() - 1 && getTile(right + 1, startY).getPieces().getFirst() != null) {
            right++;
        }

        // Collect tiles into a Word
        Word word = new Word();
        for (int x = left; x <= right; x++) {
            LetterTile tile = (LetterTile) getTile(x, startY);
            LetterPiece piece = (LetterPiece) tile.getPiece();
            if (piece != null) {
                word.addTile(tile);
                word.addScore(piece.getScore());
            }
        }

        return word;
    }

    private Word buildWordVertical(int startX, int startY) {
        // Move up to find the top
        int top = startY;
        while (top > 0 && getTile(startX, top - 1).getPieces().getFirst() != null) {
            top--;
        }

        // Move down to find the bottom
        int bottom = startY;
        while (bottom < getHeight() - 1 && getTile(startX, bottom + 1).getPieces().getFirst() != null) {
            bottom++;
        }

        // Collect tiles into a Word
        Word word = new Word();
        for (int y = top; y <= bottom; y++) {
            LetterTile tile = (LetterTile) getTile(startX, y);
            LetterPiece piece = (LetterPiece) tile.getPiece();
            if (piece != null) {
                word.addTile(tile);
                word.addScore(piece.getScore());
            }
        }

        return word;
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
