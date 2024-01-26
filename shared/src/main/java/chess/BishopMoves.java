package chess;

import java.util.ArrayList;

public class BishopMoves {
  private ChessMove move;
  private ChessPosition new_position;

  public ArrayList<ChessMove> getBishopMoves(ChessPosition position, ChessBoard board){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    ChessPiece piece = board.getPiece(position);
    int row =position.getRow();
    int col =position.getColumn();

    /* Upper right */
    int i = row + 1;
    int j = col + 1;
    while(i < 9 && j < 9){
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
      j++;
    }

    /* Upper left */
    i = row + 1;
    j = col - 1;
    while(i < 9 && j > 0){
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
      j--;
    }

    /* Lower Left */
    i = row - 1;
    j = col - 1;
    while(i > 0 && j > 0){
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
      j--;
    }

    /* Lower right */
    i = row - 1;
    j = col + 1;
    while(i > 0 && j < 9){
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
      j++;
    }

    return moveList;
  }
}
