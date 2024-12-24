package chessdemo.pieces;

import com.boardgame.core.Player;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.PieceOwnerBlacklistMovementRule;
import com.boardgame.core.model.move.RelativePositionMovementRule;

public class Knight extends ChessPiece{
    public Knight(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule notOwn = new PieceOwnerBlacklistMovementRule(null, getPlayer());
        MovementRule diagonals = new RelativePositionMovementRule(notOwn, new int[][] {{1,2},{-1,2},{1,-2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1}});

        addMovementRule(diagonals);
    }
}
