package chessdemo;

import com.boardgame.core.GamePiece;
import com.boardgame.core.model.MovementRule;
import com.boardgame.core.model.RelativePositionMovementRule;
import com.boardgame.core.model.UniqueMoveMovementRule;

public class King extends GamePiece {

    public King(boolean white, GamePiece[] enemyPieces){
        String sprite = white ? "white_king.png" : "black_king.png";
        setSprite(sprite);

        MovementRule surroundingRule = new RelativePositionMovementRule(null, new int[][]{
            {1, 1}, {1, 0}, {1, -1},
            {0, 1}, {0, -1},
            {-1, 1}, {-1, 0}, {-1, -1}
        });

        MovementRule uniqueMoveRule = new UniqueMoveMovementRule(surroundingRule, enemyPieces); // TODO: currently both kinds cannot know about each other

        addMovementRule(uniqueMoveRule);
    }

}
