package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private int rows;
    private int columns;
    private ChessPiece[][] board;
    public ChessBoard() {
        this.rows = 8;
        this.columns = 8;
        this.board = new ChessPiece[rows][columns];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                AddPieces(i,j);
            }
        }
    }

    public void removePiece(ChessPosition position){
        board[position.getRow()][position.getColumn()] = null;
    }

    public void AddPieces(int row, int col){
        /* Piece is white */
        if(row == 0){
            if(col == 0){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                board[row][col] = piece;
            }
            if(col == 1){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                board[row][col] = piece;
            }
            if(col == 2){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                board[row][col] = piece;
            }
            if(col == 3){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                board[row][col] = piece;
            }
            if(col == 4){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                board[row][col] = piece;
            }
            if(col == 5){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                board[row][col] = piece;
            }
            if(col == 6){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                board[row][col] = piece;
            }
            if(col == 7){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                board[row][col] = piece;
            }
        }
        if(row == 1){
            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[row][col] = piece;
        }

        /* Piece is black */
        if(row == 7){
            if(col == 0){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                board[row][col] = piece;
            }
            if(col == 1){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                board[row][col] = piece;
            }
            if(col == 2){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                board[row][col] = piece;
            }
            if(col == 3){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                board[row][col] = piece;
            }
            if(col == 4){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                board[row][col] = piece;
            }
            if(col == 5){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                board[row][col] = piece;
            }
            if(col == 6){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                board[row][col] = piece;
            }
            if(col == 7){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                board[row][col] = piece;
            }
        }
        if(row == 6){
            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            board[row][col] = piece;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return rows == that.rows && columns == that.columns && Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result=Objects.hash(rows, columns);
        result=31 * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public String toString() {
        return "ChessBoard{" + "rows=" + rows + ", columns=" + columns + ", board=" + Arrays.deepToString(board) + '}';
    }
}
