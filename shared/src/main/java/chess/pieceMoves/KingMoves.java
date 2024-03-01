package chess.pieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMoves {
  private ChessMove move;
  private ChessPosition newPosition;

  public ArrayList<ChessMove> getKingMoves(ChessPosition position, ChessBoard board){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    ChessPiece piece = board.getPiece(position);
    int row =position.getRow();
    int col =position.getColumn();

    /* Up */
    int rowLimit = row + 1;
    int colLimit = col;
    if(rowLimit < 9){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Down */
    rowLimit = row - 1;
    colLimit = col;
    if(rowLimit > 0){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Right */
    rowLimit = row;
    colLimit = col + 1;
    if(colLimit < 9){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Left */
    rowLimit = row;
    colLimit = col - 1;
    if(colLimit > 0){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Upper right */
    rowLimit = row + 1;
    colLimit = col + 1;
    if(rowLimit < 9 && colLimit < 9){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Upper Left */
    rowLimit = row + 1;
    colLimit = col - 1;
    if(rowLimit < 9 && colLimit > 0){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Lower Right */
    rowLimit = row - 1;
    colLimit = col + 1;
    if(rowLimit > 0 && colLimit < 9){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    /* Lower left */
    rowLimit = row - 1;
    colLimit = col - 1;
    if(rowLimit > 0 && colLimit > 0){
      newPosition = new ChessPosition(rowLimit,colLimit);
      if(board.getPiece(newPosition) == null){
        move = new ChessMove(position,newPosition,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(newPosition);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,newPosition,null);
          moveList.add(move);
        }
      }
    }

    return moveList;
  }
}
