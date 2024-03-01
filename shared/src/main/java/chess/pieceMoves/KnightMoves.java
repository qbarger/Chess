package chess.pieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMoves {
  private ChessMove move;
  private ChessPosition newPosition;

  public ArrayList<ChessMove> getKnightMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Up 2 Right 1 */
    int rowLimit = row + 2;
    int colLimit = col + 1;
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

    /* Up 2 Left 1 */
    rowLimit = row + 2;
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

    /* Down 2 Right 1 */
    rowLimit = row - 2;
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

    /* Down 2 Left 1 */
    rowLimit = row - 2;
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

    /* Right 2 Up 1 */
    rowLimit = row + 1;
    colLimit = col + 2;
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

    /* Right 2 Down 1 */
    rowLimit = row - 1;
    colLimit = col + 2;
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

    /* Left 2 Up 1 */
    rowLimit = row + 1;
    colLimit = col - 2;
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

    /* Left 2 Down 1 */
    rowLimit = row - 1;
    colLimit = col - 2;
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
