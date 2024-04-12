package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessGame.TeamColor color;
    ChessBoard board;
    ChessPiece piece;
    public ChessGame() {
        this.color = TeamColor.WHITE;
        this.board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.color = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessBoard placeHolder = new ChessBoard(board);
        Collection<ChessMove> moveList;
        Collection<ChessMove> validMoveList = new ArrayList<>();
        piece=board.getPiece(startPosition);
        moveList=piece.pieceMoves(board, startPosition);
        Iterator<ChessMove> iterator = moveList.iterator();
        while(iterator.hasNext()){
            ChessMove move = iterator.next();
            ChessPosition endPosition = move.getEndPosition();
            board.addPiece(endPosition, piece);
            board.removePiece(startPosition);
            if(!isInCheck(piece.getTeamColor())){
                validMoveList.add(move);
            }
            board = new ChessBoard(placeHolder);
        }
        return validMoveList;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moveList;
        moveList = validMoves(move.getStartPosition());
        if(piece.getTeamColor() == getTeamTurn()) {
            if (moveList.contains(move)) {
                ChessPosition startPosition=move.getStartPosition();
                ChessPosition endPosition=move.getEndPosition();
                piece=board.getPiece(startPosition);
                if (move.getPromotionPiece() != null) {
                    piece.setPieceType(move.getPromotionPiece());
                }
                board.addPiece(endPosition, piece);
                board.removePiece(startPosition);
            } else {
                throw new InvalidMoveException("Invalid move");
            }
        }
        else {
            throw new InvalidMoveException("Invalid move");
        }
        if(color == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for(int row = 1;row < 9;row++){
            for(int col = 1;col < 9;col++){
                ChessPosition position = new ChessPosition(row,col);
                if(board.getPiece(position) != null) {
                    ChessPiece piece=board.getPiece(position);
                    if (piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> moveList;
                        moveList=piece.pieceMoves(board,position);
                        for (ChessMove move : moveList) {
                            ChessPosition checkPosition=move.getEndPosition();
                            if(board.getPiece(checkPosition) != null) {
                                ChessPiece checkPiece=board.getPiece(checkPosition);
                                if (checkPiece.getPieceType() == ChessPiece.PieceType.KING && checkPiece.getTeamColor() == teamColor) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor) && isInStalemate(teamColor)){
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessBoard placeHolder = new ChessBoard(board);
        Collection<ChessMove> moveList;
        for(int row = 1;row < 9;row++){
            for(int col = 1;col < 9;col++){
                ChessPosition position = new ChessPosition(row,col);
                if(board.getPiece(position) != null) {
                    piece=board.getPiece(position);
                    if (piece.getTeamColor() == teamColor) {
                        moveList=validMoves(position);
                        if (!moveList.isEmpty()) {
                            board = new ChessBoard(placeHolder);
                            return false;
                        }
                    }
                }
            }
            board = new ChessBoard(placeHolder);
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return color == chessGame.color && Objects.equals(board, chessGame.board) && Objects.equals(piece, chessGame.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, board, piece);
    }

    @Override
    public String toString() {
        return "ChessGame{" + "color=" + color + ", board=" + board + ", piece=" + piece + '}';
    }
}


