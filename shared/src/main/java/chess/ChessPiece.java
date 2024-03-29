package chess;

import chess.pieceMoves.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;
    private BishopMoves bishop;
    private RookMoves rook;
    private QueenMoves queen;
    private KnightMoves knight;
    private KingMoves king;
    private PawnMoves pawn;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public void setPieceType(PieceType promotionPiece){
        type = promotionPiece;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);

        if(piece.getPieceType() == PieceType.BISHOP){
            bishop = new BishopMoves();
            moveList = bishop.getBishopMoves(myPosition,board);
        }
        if(piece.getPieceType() == PieceType.ROOK){
            rook = new RookMoves();
            moveList = rook.getRookMoves(myPosition,board);
        }
        if(piece.getPieceType() == PieceType.QUEEN){
            queen = new QueenMoves();
            moveList = queen.getQueenMoves(myPosition,board);
        }
        if(piece.getPieceType() == PieceType.KING){
            king = new KingMoves();
            moveList = king.getKingMoves(myPosition,board);
        }
        if(piece.getPieceType() == PieceType.KNIGHT){
            knight = new KnightMoves();
            moveList = knight.getKnightMoves(myPosition,board);
        }
        if(piece.getPieceType() == PieceType.PAWN){
            pawn = new PawnMoves();
            moveList = pawn.getPawnMoves(myPosition,board);
        }

        return moveList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" + "pieceColor=" + pieceColor + ", type=" + type + '}';
    }
}
