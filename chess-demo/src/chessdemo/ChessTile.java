package chessdemo;

import chessdemo.pieces.ChessPiece;
import chessdemo.pieces.King;
import com.boardgame.core.GameBoard;
import com.boardgame.core.GamePiece;
import com.boardgame.core.Tile;

public class ChessTile extends Tile {

    public ChessTile(int x, int y, GameBoard board) {
        super(x, y, 2, board); // Fixed cap of 2
        String whiteSprite = "white.png";
        String blackSprite = "black.png";
        setSprite((x+y)%2 == 0 ? whiteSprite : blackSprite); // make even tiles white
    }

    @Override
    public void onPieceEnter(GamePiece piece){
        /*
        for (GamePiece p : getPieces()){
            if (p.getOwner() != piece.getOwner()){
                removePiece(p);
                System.out.println("Removing piece");
            }
        }
        */
        for (GamePiece p : getPieces()){
            System.out.println(p.getClass().getSimpleName());
            System.out.println(p.getOwner() == piece.getOwner());
            if (p != piece) {
                if (p instanceof King){
                    p.getOwner().endGame();
                }
                removePiece(p);
                piece.getOwner().addPieceToHand(p);
            }
        }
    }
}
