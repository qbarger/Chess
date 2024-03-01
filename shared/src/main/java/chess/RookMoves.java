package chess;

import java.util.ArrayList;

public class RookMoves {
  private ChessMove move;
  private ChessPosition newPosition;

  public ArrayList<ChessMove> getRookMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Up */
    int rowLimit = row + 1;
    int colLimit = col;

    while(rowLimit < 9){
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
          break;
        }
        else {
          break;
        }
      }
      rowLimit++;
    }

    /* Down */
    rowLimit = row - 1;
    colLimit = col;

    while(rowLimit > 0){
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
          break;
        }
        else {
          break;
        }
      }
      rowLimit--;
    }

    /* Right */
    rowLimit = row;
    colLimit = col + 1;

    while(colLimit < 9){
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
          break;
        }
        else {
          break;
        }
      }
      colLimit++;
    }

    /* Left */
    rowLimit = row;
    colLimit = col - 1;

    while(colLimit > 0){
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
          break;
        }
        else {
          break;
        }
      }
      colLimit--;
    }

    return moveList;
  }
}
