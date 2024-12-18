package chessdemo.pieces;

import com.boardgame.core.GamePiece;

public abstract class ChessPiece extends GamePiece {
    public ChessPiece(boolean white) {
        String sprite =
                white ? "white_"+ getClass().getSimpleName().toLowerCase() +".png" :
                        "black_"+ getClass().getSimpleName().toLowerCase() +".png";
        setSprite(sprite);

        int up = white ? -1 : 1;
        setupMovementRules(up);
    }

    protected abstract void setupMovementRules(int up);
}
