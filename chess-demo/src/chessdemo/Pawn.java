package chessdemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.model.MoveCountLessMovementRule;
import com.boardgame.core.model.MovementRule;
import com.boardgame.core.model.PositionMovementRule;

public class Pawn extends GamePiece{
    public Pawn(boolean white){
        setSprite("pawn.png");

        int up;

        if (white) up = -1;
        else up = 1;

        MovementRule firstMoveMR =
                new MoveCountLessMovementRule(
                        new PositionMovementRule(null, new int[][]{{0, up}, {0, up*2}}),
                        1);

        MovementRule upMR = new PositionMovementRule(null, new int[][]{{0, up}});
        MovementRule downMR = new PositionMovementRule(null, new int[][]{{0, -up}});

        addMovementRule(firstMoveMR);
        addMovementRule(downMR);
        addMovementRule(upMR);
    }
}
