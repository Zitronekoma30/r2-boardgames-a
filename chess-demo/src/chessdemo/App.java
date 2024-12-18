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
        int pawnRow = white ? 6 : 1;
        int mainRow = white ? 7 : 0;
        int queenColumn = 3;
        int kingColumn = 4;

        // Add pawns
        for (int y = 0; y < 8; y++) {
            Pawn pawn = new Pawn(white);
            player.addPiece(pawn);
            board.placePiece(pawnRow, y, pawn);
        }

        // Add rooks
        Rook rook1 = new Rook(white);
        Rook rook2 = new Rook(white);
        player.addPiece(rook1);
        player.addPiece(rook2);
        board.placePiece(mainRow, 0, rook1);
        board.placePiece(mainRow, 7, rook2);

        // Add knights
        Knight knight1 = new Knight(white);
        Knight knight2 = new Knight(white);
        player.addPiece(knight1);
        player.addPiece(knight2);
        board.placePiece(mainRow, 1, knight1);
        board.placePiece(mainRow, 6, knight2);

        // Add bishops
        Bishop bishop1 = new Bishop(white);
        Bishop bishop2 = new Bishop(white);
        player.addPiece(bishop1);
        player.addPiece(bishop2);
        board.placePiece(mainRow, 2, bishop1);
        board.placePiece(mainRow, 5, bishop2);

        // Add queen
        Queen queen = new Queen(white);
        player.addPiece(queen);
        board.placePiece(mainRow, queenColumn, queen);

        // Add king
        King king = new King(white, new GamePiece[]{});
        player.addPiece(king);
        board.placePiece(mainRow, kingColumn, king);
    }
}

