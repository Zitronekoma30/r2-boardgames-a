package scrabbledemo;

import com.boardgame.core.GameBoard;
import com.boardgame.core.model.hand.Action;
import com.boardgame.core.model.hand.PlayValidator;

import java.util.*;

public class LetterPlayValidator extends PlayValidator {

    @Override
    public boolean validate(Action[] actions, GameBoard gameBoard) {
        // check if any of the new pieces are adjacent to the origin or another piece
        WordBoard board = (WordBoard) gameBoard;
        var allowedPos = board.getAllowedTiles();

        boolean found = false;

        for (Action a : actions){
            if (allowedPos.contains((LetterTile) a.to())) found = true;
        }

        if (!found) return false;

        // Check if all destinations are adjacent (non diagonal) (simple BFS)
        var positions = Arrays.stream(actions).map(action -> new int[]{action.to().getX(), action.to().getY()}).toArray(int[][]::new);
        if (positions.length == 0) return false;

        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(positions[0]);
        visited.add(Arrays.toString(positions[0]));

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int count = 1;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            for (int[] dir : directions) {
                int[] neighbor = {pos[0] + dir[0], pos[1] + dir[1]};
                if (Arrays.stream(positions).anyMatch(p -> Arrays.equals(p, neighbor)) && !visited.contains(Arrays.toString(neighbor))) {
                    queue.add(neighbor);
                    visited.add(Arrays.toString(neighbor));
                    count++;
                }
            }
        }

        if (count != positions.length) return false;

        // Mark all pieces as "new" so the board can recognize them and roll back if needed
        for (Action a : actions){
            LetterPiece piece = (LetterPiece) a.piece();
            piece.setNewlyPlaced(true);
        }

        return true;
    }
}
