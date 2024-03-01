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
    int rowLimit = row + 1;
    int colLimit = col + 1;
    while(rowLimit < 9 && colLimit < 9){
      new_position = new ChessPosition(rowLimit,colLimit);
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
      rowLimit++;
      colLimit++;
    }

    /* Upper left */
    rowLimit = row + 1;
    colLimit = col - 1;
    while(rowLimit < 9 && colLimit > 0){
      new_position = new ChessPosition(rowLimit,colLimit);
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
      rowLimit++;
      colLimit--;
    }

    /* Lower Left */
    rowLimit = row - 1;
    colLimit = col - 1;
    while(rowLimit > 0 && colLimit > 0){
      new_position = new ChessPosition(rowLimit,colLimit);
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
      rowLimit--;
      colLimit--;
    }

    /* Lower right */
    rowLimit = row - 1;
    colLimit = col + 1;
    while(rowLimit > 0 && colLimit < 9){
      new_position = new ChessPosition(rowLimit,colLimit);
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
      rowLimit--;
      colLimit++;
    }

    return moveList;
  }
}
