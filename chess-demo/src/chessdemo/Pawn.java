package chessdemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.model.MoveCountLessMovementRule;
import com.boardgame.core.model.MovementRule;
import com.boardgame.core.model.RelativePositionMovementRule;

public class Pawn extends GamePiece{
    public Pawn(boolean white){
        String sprite = white ? "white_pawn.png" : "black_pawn.png";
        setSprite(sprite);

        int up;

        if (white) up = -1;
        else up = 1;

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
