package chessdemo.pieces;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.PieceOwnerBlacklistMovementRule;
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
        MovementRule notOwn = new PieceOwnerBlacklistMovementRule(null, getPlayer());

        MovementRule surroundingRule = new RelativePositionMovementRule(notOwn, new int[][]{
                {1, 1}, {1, 0}, {1, -1},
                {0, 1}, {0, -1},
                {-1, 1}, {-1, 0}, {-1, -1}
        });

        // cutting this and check functionality due to time constraints.
        // It's possible with the frameworks current abilities, I just can't do it by myself in the time I have left.
        //MovementRule uniqueMoveRule = new UniqueMoveMovementRule(surroundingRule, enemyPieces); // TODO: currently both kinds cannot know about each other

        addMovementRule(surroundingRule);
    }
}
