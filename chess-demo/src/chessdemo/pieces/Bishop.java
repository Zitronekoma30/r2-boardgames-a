package chessdemo.pieces;

import com.boardgame.core.Player;

public class Bishop extends ChessPiece{
    public Bishop(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
    }
}
