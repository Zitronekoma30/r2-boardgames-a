package chessdemo.pieces;

import com.boardgame.core.model.move.ContinuousMovementRule;

public class Rook extends ChessPiece{
    public Rook(boolean white) {
        super(white);
    }

    @Override
    protected void setupMovementRules(int up) {
        addMovementRule(new ContinuousMovementRule(null, new int[] {0, 1}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {0, -1}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {1, 0}, 8));
        addMovementRule(new ContinuousMovementRule(null, new int[] {-1, 0}, 8));
    }
}