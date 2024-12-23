package chessdemo.pieces;

import com.boardgame.core.Player;

public class Queen extends ChessPiece {
    public Queen(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
    }
}
