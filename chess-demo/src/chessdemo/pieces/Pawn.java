package chessdemo.pieces;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.model.move.*;

public class Pawn extends ChessPiece{

    public Pawn(Player player, Player opponent) {
        super(player, opponent);
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule notOwn = new PieceOwnerBlacklistMovementRule(null, getPlayer());

        MovementRule firstMoveMR =
                new MoveCountLessMovementRule(
                        new RelativePositionMovementRule(
                                new IsEmptyMovementRule(null), new int[][]{{0, up}, {0, up*2}}), 1);

        MovementRule upMR = new RelativePositionMovementRule(new IsEmptyMovementRule(null), new int[][]{{0, up}});

        MovementRule diagonalMR = new RelativePositionMovementRule(
                new InvertDiscreteMovementRule(
                        notOwn, new IsEmptyMovementRule(null)), new int[][]{{1, up}, {-1, up}});

        addMovementRule(firstMoveMR);
        addMovementRule(upMR);
        addMovementRule(diagonalMR);
    }
}
