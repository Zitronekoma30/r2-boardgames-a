package scrabbledemo;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private List<LetterTile> tiles;
    private int score;

    public Word() {
        this.tiles = new ArrayList<>();
        this.score = 0;
    }

    public void addTile(LetterTile tile) {
        tiles.add(tile);
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public String toString() {
        StringBuilder word = new StringBuilder();
        for (LetterTile tile : tiles) {
            word.append((tile.getPiece()).getLetter());
        }
        return word.toString();
    }

    public int getScore() {
        return score;
    }

    public boolean containsNew(){
        for (LetterTile tile : tiles){
            if (tile.getPiece().getNewlyPlaced()) return true;
        }
        return false;
    }

    public List<LetterTile> getTiles(){
        return tiles;
    }
}