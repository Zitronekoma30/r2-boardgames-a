package chessdemo.pieces;

import com.boardgame.core.Player;
import com.boardgame.core.model.move.ContinuousMovementRule;

public class Rook extends ChessPiece{
    public Rook(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
        addMovementRule(new ContinuousMovementRule(null, new int[] {0, 1}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {0, -1}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {1, 0}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {-1, 0}, 8));
    }
}