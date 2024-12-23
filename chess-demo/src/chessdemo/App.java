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

        var white = new Player("white", new int[]{1, -1});
        var black = new Player("black", new int[]{1, 1});

        gm.addPlayer(white);
        gm.addPlayer(black);

        Rook r = new Rook(white, black);
        Pawn p1 = new Pawn(black, white);
        Pawn p2 = new Pawn(white, black);

        white.addPiece(r);
        white.addPiece(p2);

        black.addPiece(p1);

        board.placePiece(4,4, r);
        board.placePiece(4,7, p1);
        board.placePiece(4, 1, p2);

        // Add pieces
        //addPieces(white, black, true, board);
        //addPieces(black, white, false, board);

        GameManager.getInstance().startGame();
        GameManager.getInstance().printBoardJson();
    }

    private static void addPieces(Player player, Player opponent, boolean white, GameBoard board) {
        int mainRow = white ? 7 : 0; // y:0 is black's home row, y:7 is white's home row
        int pawnRow = white ? 6 : 1;
        int queenColumn = 3;
        int kingColumn = 4;

        // Add pawns
        for (int x = 0; x < 8; x++) { // Iterate through columns for pawns
            Pawn pawn = new Pawn(player, opponent);
            player.addPiece(pawn);
            board.placePiece(x, pawnRow, pawn); // Place pawns on the pawnRow
        }

        // Add rooks
        Rook rook1 = new Rook(player, opponent);
        Rook rook2 = new Rook(player, opponent);
        player.addPiece(rook1);
        player.addPiece(rook2);
        board.placePiece(0, mainRow, rook1); // Place rook at column 0
        board.placePiece(7, mainRow, rook2); // Place rook at column 7

        // Add knights
        Knight knight1 = new Knight(player, opponent);
        Knight knight2 = new Knight(player, opponent);
        player.addPiece(knight1);
        player.addPiece(knight2);
        board.placePiece(1, mainRow, knight1); // Place knight at column 1
        board.placePiece(6, mainRow, knight2); // Place knight at column 6

        // Add bishops
        Bishop bishop1 = new Bishop(player, opponent);
        Bishop bishop2 = new Bishop(player, opponent);
        player.addPiece(bishop1);
        player.addPiece(bishop2);
        board.placePiece(2, mainRow, bishop1); // Place bishop at column 2
        board.placePiece(5, mainRow, bishop2); // Place bishop at column 5

        // Add queen
        Queen queen = new Queen(player, opponent);
        player.addPiece(queen);
        board.placePiece(queenColumn, mainRow, queen); // Place queen at column 3

        // Add king
        King king = new King(player, opponent, new GamePiece[]{});
        player.addPiece(king);
        board.placePiece(kingColumn, mainRow, king); // Place king at column 4
    }

}

