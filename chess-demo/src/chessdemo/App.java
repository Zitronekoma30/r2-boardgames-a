package chessdemo;

import chessdemo.pieces.*;
import com.boardgame.core.GameManager;
import com.boardgame.core.GameBoard;
import com.boardgame.core.GamePiece;
import com.boardgame.core.Player;

public class App {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(8, 8); // instantiate empty 8x8 chess board

        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                board.setTile(x,y, new ChessTile(x, y, board));
            }
        }

        GameManager gm = GameManager.getInstance();
        gm.setBoard(board);

        var white = new Player();
        var black = new Player();
        gm.addPlayer(white);
        gm.addPlayer(black);

        // Add pieces
        addPieces(white, true, board);
        addPieces(black, false, board);

        GameManager.getInstance().startGame();
        GameManager.getInstance().printBoardJson();
    }

    private static void addPieces(Player player, boolean white, GameBoard board) {
        int mainRow = white ? 7 : 0; // y:0 is black's home row, y:7 is white's home row
        int pawnRow = white ? 6 : 1;
        int queenColumn = 3;
        int kingColumn = 4;

        // Add pawns
        for (int x = 0; x < 8; x++) { // Iterate through columns for pawns
            Pawn pawn = new Pawn(white);
            player.addPiece(pawn);
            board.placePiece(x, pawnRow, pawn); // Place pawns on the pawnRow
        }

        // Add rooks
        Rook rook1 = new Rook(white);
        Rook rook2 = new Rook(white);
        player.addPiece(rook1);
        player.addPiece(rook2);
        board.placePiece(0, mainRow, rook1); // Place rook at column 0
        board.placePiece(7, mainRow, rook2); // Place rook at column 7

        // Add knights
        Knight knight1 = new Knight(white);
        Knight knight2 = new Knight(white);
        player.addPiece(knight1);
        player.addPiece(knight2);
        board.placePiece(1, mainRow, knight1); // Place knight at column 1
        board.placePiece(6, mainRow, knight2); // Place knight at column 6

        // Add bishops
        Bishop bishop1 = new Bishop(white);
        Bishop bishop2 = new Bishop(white);
        player.addPiece(bishop1);
        player.addPiece(bishop2);
        board.placePiece(2, mainRow, bishop1); // Place bishop at column 2
        board.placePiece(5, mainRow, bishop2); // Place bishop at column 5

        // Add queen
        Queen queen = new Queen(white);
        player.addPiece(queen);
        board.placePiece(queenColumn, mainRow, queen); // Place queen at column 3

        // Add king
        King king = new King(white, new GamePiece[]{});
        player.addPiece(king);
        board.placePiece(kingColumn, mainRow, king); // Place king at column 4
    }

}

