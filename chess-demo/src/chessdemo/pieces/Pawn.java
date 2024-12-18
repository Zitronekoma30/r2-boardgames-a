package chessdemo.pieces;

import com.boardgame.core.GamePiece;
import com.boardgame.core.model.MoveCountLessMovementRule;
import com.boardgame.core.model.MovementRule;
import com.boardgame.core.model.RelativePositionMovementRule;

public class Pawn extends ChessPiece{

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule firstMoveMR =
                new MoveCountLessMovementRule(
                        new RelativePositionMovementRule(
                                null, new int[][]{{0, up}, {0, up*2}}), 1);

        MovementRule upMR = new RelativePositionMovementRule(null, new int[][]{{0, up}});
        // TODO: supplement with is empty movement rule to prevent moving forward into a piece
        // TODO: supplement with inverted is empty movement rule to prevent moving diagonally into an empty space

        addMovementRule(firstMoveMR);
        addMovementRule(upMR);
    }
}
