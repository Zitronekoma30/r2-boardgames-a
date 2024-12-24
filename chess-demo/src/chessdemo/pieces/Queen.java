package chessdemo.pieces;

import com.boardgame.core.Player;
import com.boardgame.core.model.move.ContinuousMovementRule;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.PieceOwnerBlacklistMovementRule;

public class Queen extends ChessPiece {
    public Queen(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule notOwn = new PieceOwnerBlacklistMovementRule(null, getPlayer());
        // Straight
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {0, 1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {0, -1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {1, 0}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {-1, 0}, 8));

        // Diagonal
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {1, 1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {1, -1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {-1, -1}, 8));
        addMovementRule(new ContinuousMovementRule(notOwn, new int[] {-1, 1}, 8));
    }
}
