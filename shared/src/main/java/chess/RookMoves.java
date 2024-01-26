package chess;

import java.util.ArrayList;

public class RookMoves {
  private ChessMove move;
  private ChessPosition new_position;

  public ArrayList<ChessMove> getRookMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Up */
    int i = row + 1;
    int j = col;

    while(i < 9){
      new_position = new ChessPosition(i,j);
      if(board.getPiece(new_position) == null){
        move = new ChessMove(position,new_position,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(new_position);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          break;
        }
        else {
          break;
        }
      }
      i++;
    }

    /* Down */
    i = row - 1;
    j = col;

    while(i > 0){
      new_position = new ChessPosition(i,j);
      if(board.getPiece(new_position) == null){
        move = new ChessMove(position,new_position,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(new_position);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          break;
        }
        else {
          break;
        }
      }
      i--;
    }

    /* Right */
    i = row;
    j = col + 1;

    while(j < 9){
      new_position = new ChessPosition(i,j);
      if(board.getPiece(new_position) == null){
        move = new ChessMove(position,new_position,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(new_position);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          break;
        }
        else {
          break;
        }
      }
      j++;
    }

    /* Left */
    i = row;
    j = col - 1;

    while(j > 0){
      new_position = new ChessPosition(i,j);
      if(board.getPiece(new_position) == null){
        move = new ChessMove(position,new_position,null);
        moveList.add(move);
      }
      else {
        ChessPiece other = board.getPiece(new_position);
        if(other.getTeamColor() != piece.getTeamColor()){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          break;
        }
        else {
          break;
        }
      }
      j--;
    }

    return moveList;
  }
}
