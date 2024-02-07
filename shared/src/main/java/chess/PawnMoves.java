package chess;

import java.util.ArrayList;

public class PawnMoves {
  private ChessMove move;
  private ChessPosition new_position;

  public ArrayList<ChessMove> getPawnMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Pawn is white */
    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
      int i = row + 1;
      int j = col;
      new_position = new ChessPosition(i,j);
      if(row == 2){
        /* Single Move */
        if(board.getPiece(new_position) == null){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          new_position = new ChessPosition(i + 1,j);
          /* Double Move */
          if(board.getPiece(new_position) == null){
            move = new ChessMove(position,new_position,null);
            moveList.add(move);
          }
        }
        /* Start capture */
        new_position = new ChessPosition(i,j + 1);
        if((j + 1) < 9) {
          if (board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
          }
        }
        new_position = new ChessPosition(i,j - 1);
        if((j - 1) > 0) {
          if (board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
          }
        }
      }
      else {
        /* Promotion */
        if(row == 7){
          if(board.getPiece(new_position) == null){
            move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
            moveList.add(move);
          }
          /* Promotion Capture */
          new_position = new ChessPosition(i,j + 1);
          if(j < 9) {
            if (board.getPiece(new_position) != null) {
              ChessPiece other=board.getPiece(new_position);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
                moveList.add(move);
              }
            }
          }
          new_position = new ChessPosition(i,j - 1);
          if(j > 0) {
            if (board.getPiece(new_position) != null) {
              ChessPiece other=board.getPiece(new_position);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
                moveList.add(move);
                move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
                moveList.add(move);
              }
            }
          }
        }
        else {
          if(i < 9){
            if(board.getPiece(new_position) == null) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
            /* Capture */
            new_position = new ChessPosition(i,j + 1);
            if((j + 1) < 9) {
              if (board.getPiece(new_position) != null) {
                ChessPiece other=board.getPiece(new_position);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, new_position, null);
                  moveList.add(move);
                }
              }
            }
            new_position = new ChessPosition(i,j - 1);
            if((j - 1) > 0) {
              if (board.getPiece(new_position) != null) {
                ChessPiece other=board.getPiece(new_position);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, new_position, null);
                  moveList.add(move);
                }
              }
            }
          }
        }
      }
    }

    /* Pawn is black */
    else {
      int i = row - 1;
      int j = col;
      new_position = new ChessPosition(i,j);
      if(row == 7){
        /* Single Move */
        if(board.getPiece(new_position) == null){
          move = new ChessMove(position,new_position,null);
          moveList.add(move);
          new_position = new ChessPosition(i - 1,j);
          /* Double Move */
          if(board.getPiece(new_position) == null){
            move = new ChessMove(position,new_position,null);
            moveList.add(move);
          }
        }
        /* Start Capture */
        new_position = new ChessPosition(i,j + 1);
        if((j + 1) < 9) {
          if (board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
          }
        }
        new_position = new ChessPosition(i,j - 1);
        if((j - 1) > 0) {
          if (board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
          }
        }
      }
      else {
        /* Promotion */
        if(row == 2){
          if(board.getPiece(new_position) == null){
            move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
            moveList.add(move);
            move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
            moveList.add(move);
          }
          /* Promotion Capture */
          new_position = new ChessPosition(i,j + 1);
          if(board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
              moveList.add(move);
            }
          }
          new_position = new ChessPosition(i,j - 1);
          if(board.getPiece(new_position) != null) {
            ChessPiece other=board.getPiece(new_position);
            if (other.getTeamColor() != piece.getTeamColor()) {
              move=new ChessMove(position, new_position, ChessPiece.PieceType.BISHOP);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.KNIGHT);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.ROOK);
              moveList.add(move);
              move=new ChessMove(position, new_position, ChessPiece.PieceType.QUEEN);
              moveList.add(move);
            }
          }
        }
        else {
          if(i > 0){
            if(board.getPiece(new_position) == null) {
              move=new ChessMove(position, new_position, null);
              moveList.add(move);
            }
            /* Capture */
            new_position = new ChessPosition(i,j + 1);
            if((j + 1) < 9) {
              if (board.getPiece(new_position) != null) {
                ChessPiece other=board.getPiece(new_position);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, new_position, null);
                  moveList.add(move);
                }
              }
            }
            new_position = new ChessPosition(i,j - 1);
            if((j - 1) > 0) {
              if (board.getPiece(new_position) != null) {
                ChessPiece other=board.getPiece(new_position);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, new_position, null);
                  moveList.add(move);
                }
              }
            }
          }
        }
      }
    }

    return moveList;
  }
}
