package chessdemo.pieces;

import com.boardgame.core.Player;

public class Knight extends ChessPiece{
    public Knight(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
    }
}
