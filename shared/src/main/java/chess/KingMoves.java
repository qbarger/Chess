package chess;

import java.util.ArrayList;

public class KingMoves {
  private ChessMove move;
  private ChessPosition new_position;

  public ArrayList<ChessMove> getKingMoves(ChessPosition position, ChessBoard board){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    ChessPiece piece = board.getPiece(position);
    int row =position.getRow();
    int col =position.getColumn();

    /* Up */
    int i = row + 1;
    int j = col;
    if(i < 9){
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
        }
      }
    }

    /* Down */
    i = row - 1;
    j = col;
    if(i > 0){
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
        }
      }
    }

    /* Right */
    i = row;
    j = col + 1;
    if(j < 9){
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
        }
      }
    }

    /* Left */
    i = row;
    j = col - 1;
    if(j > 0){
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
        }
      }
    }

    /* Upper right */
    i = row + 1;
    j = col + 1;
    if(i < 9 && j < 9){
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
        }
      }
    }

    /* Upper Left */
    i = row + 1;
    j = col - 1;
    if(i < 9 && j > 0){
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
        }
      }
    }

    /* Lower Right */
    i = row - 1;
    j = col + 1;
    if(i > 0 && j < 9){
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
        }
      }
    }

    /* Lower left */
    i = row - 1;
    j = col - 1;
    if(i > 0 && j > 0){
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
        }
      }
    }

    return moveList;
  }
}
