package chessdemo.pieces;

import com.boardgame.core.Player;
import com.boardgame.core.model.move.ContinuousMovementRule;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.PieceOwnerBlacklistMovementRule;

public class Bishop extends ChessPiece{
    public Bishop(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule notOwn = new PieceOwnerBlacklistMovementRule(null, getPlayer());
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {1, 1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {1, -1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {-1, -1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {-1, 1}, 8));
    }
}
