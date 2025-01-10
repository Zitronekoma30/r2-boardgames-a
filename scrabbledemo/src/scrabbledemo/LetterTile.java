package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.Tile;

import java.util.Objects;

public class LetterTile extends Tile {
    private int scoreMult = 1;

    public LetterTile(int x, int y, GameBoard board) {
        super(x, y, 1, board);
        String sprite = "tile_" + (((x+y) % 2 == 0) ? "black" : "white") + ".png";
        setSprite(sprite);
    }

    public LetterTile(int x, int y, GameBoard board, int mult) {
        this(x,y, board);
        this.scoreMult = mult;
    }

    public int getScoreMult(){
        return scoreMult;
    }

    public void setMiddle(){
        setSprite("tile_red.png");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LetterTile oTile = (LetterTile) o;
        return getX() == oTile.getX() && getY() == oTile.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public LetterPiece getPiece(){
        return (LetterPiece) getPieces().getFirst();
    }
}