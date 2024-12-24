package chessdemo.pieces;

import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;
import com.boardgame.core.model.move.MovementRule;
import com.boardgame.core.model.move.PieceOwnerBlacklistMovementRule;

public abstract class ChessPiece extends GamePiece {
    private Player player;
    private Player opponent;

    public ChessPiece(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;

        String sprite = player.getName() + "_" + getClass().getSimpleName().toLowerCase() +".png";
        setSprite(sprite);

        int up = player.getDirections()[1];
        setupMovementRules(up);
    }

    public Player getPlayer() { return player; }
    public Player getOpponent() { return opponent; }

    protected abstract void setupMovementRules(int up);
}
