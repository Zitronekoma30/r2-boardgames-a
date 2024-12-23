package chessdemo.pieces;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.RelativePositionMovementRule;
import com.boardgame.core.model.move.UniqueMoveMovementRule;

public class King extends ChessPiece {
    private final GamePiece[] enemyPieces;

    public King(Player player, Player opponent, GamePiece[] enemyPieces) {
        super(player, opponent);
        this.enemyPieces = enemyPieces;
    }

    @Override
    protected void setupMovementRules(int up) {
        MovementRule surroundingRule = new RelativePositionMovementRule(null, new int[][]{
                {1, 1}, {1, 0}, {1, -1},
                {0, 1}, {0, -1},
                {-1, 1}, {-1, 0}, {-1, -1}
        });

        MovementRule uniqueMoveRule = new UniqueMoveMovementRule(surroundingRule, enemyPieces); // TODO: currently both kinds cannot know about each other

        addMovementRule(uniqueMoveRule);
    }
}
