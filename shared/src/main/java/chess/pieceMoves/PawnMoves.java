package chess.pieceMoves;

import chess.*;

import java.util.ArrayList;

public class PawnMoves {
  private ChessMove move;
  private ChessPosition newPosition;

  public ArrayList<ChessMove> getPawnMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Pawn is black */
    if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
      moveList.addAll(blackMoves(board, piece, position, row, col));
    }

    /* Pawn is white */
    else {
      moveList.addAll(whiteMoves(board, piece, position, row, col));
    }

    return moveList;
  }

  public ArrayList<ChessMove> whiteMoves(ChessBoard board, ChessPiece piece, ChessPosition position, int row, int col){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    int rowLimit = row + 1;
    int colLimit = col;
    if(rowLimit < 9) {
      newPosition=new ChessPosition(rowLimit, colLimit);
      if (row == 2) {
        /* Single Move */
        if (board.getPiece(newPosition) == null) {
          move=new ChessMove(position, newPosition, null);
          moveList.add(move);
          newPosition=new ChessPosition(rowLimit + 1, colLimit);
          /* Double Move */
          if (board.getPiece(newPosition) == null) {
            move=new ChessMove(position, newPosition, null);
            moveList.add(move);
          }
        }
        /* Start capture */
        newPosition=new ChessPosition(rowLimit, colLimit + 1);
        moveList.add(pawnCaptureRight(board, piece, position));
        newPosition=new ChessPosition(rowLimit, colLimit - 1);
        moveList.add(pawnCaptureLeft(board, piece, position));
      } else {
        /* Promotion */
        if (row == 7) {
          if (board.getPiece(newPosition) == null) {
            moveList.addAll(promotion(position));
          }
          /* Promotion Capture */
          newPosition=new ChessPosition(rowLimit, colLimit + 1);
          if (colLimit < 9) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                moveList.addAll(promotion(position));
              }
            }
          }
          newPosition=new ChessPosition(rowLimit, colLimit - 1);
          if (colLimit > 0) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                moveList.addAll(promotion(position));
              }
            }
          }
        } else {
          if (rowLimit < 9) {
            if (board.getPiece(newPosition) == null) {
              move=new ChessMove(position, newPosition, null);
              moveList.add(move);
            }
            /* Capture */
            newPosition=new ChessPosition(rowLimit, colLimit + 1);
            moveList.add(pawnCaptureRight(board, piece, position));

            newPosition=new ChessPosition(rowLimit, colLimit - 1);
            moveList.add(pawnCaptureLeft(board, piece, position));
          }
        }
      }
    }
  }

  public ArrayList<ChessMove> blackMoves(ChessBoard board, ChessPiece piece, ChessPosition position, int row, int col){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    int rowLimit=row - 1;
    int colLimit=col;
    if(rowLimit >= 0) {
      newPosition=new ChessPosition(rowLimit, colLimit);
      if (row == 7) {
        /* Single Move */
        if (board.getPiece(newPosition) == null) {
          move=new ChessMove(position, newPosition, null);
          moveList.add(move);
          newPosition=new ChessPosition(rowLimit - 1, colLimit);
          /* Double Move */
          if (board.getPiece(newPosition) == null) {
            move=new ChessMove(position, newPosition, null);
            moveList.add(move);
          }
        }
        /* Start Capture */
        newPosition=new ChessPosition(rowLimit, colLimit + 1);
        moveList.add(pawnCaptureRight(board, piece, position));

        newPosition=new ChessPosition(rowLimit, colLimit - 1);
        moveList.add(pawnCaptureLeft(board, piece, position));
      } else {
        /* Promotion */
        if (row == 2) {
          if (board.getPiece(newPosition) == null) {
            moveList.addAll(promotion(position));
          }
          /* Promotion Capture */
          newPosition=new ChessPosition(rowLimit, colLimit + 1);
          if (board.getPiece(newPosition) != null) {
            ChessPiece other=board.getPiece(newPosition);
            if (other.getTeamColor() != piece.getTeamColor()) {
              moveList.addAll(promotion(position));
            }
          }
          newPosition=new ChessPosition(rowLimit, colLimit - 1);
          if (board.getPiece(newPosition) != null) {
            ChessPiece other=board.getPiece(newPosition);
            if (other.getTeamColor() != piece.getTeamColor()) {
              moveList.addAll(promotion(position));
            }
          }
        } else {
          if (rowLimit > 0) {
            if (board.getPiece(newPosition) == null) {
              move=new ChessMove(position, newPosition, null);
              moveList.add(move);
            }
            /* Capture */
            newPosition=new ChessPosition(rowLimit, colLimit + 1);
            moveList.add(pawnCaptureRight(board, piece, position));

            newPosition=new ChessPosition(rowLimit, colLimit - 1);
            moveList.add(pawnCaptureLeft(board, piece, position));
          }
        }
      }
    }
    return moveList;
  }
  public ChessMove pawnCaptureRight(ChessBoard board, ChessPiece piece, ChessPosition position){
    if ((newPosition.getColumn()) < 9) {
      if (board.getPiece(newPosition) != null) {
        ChessPiece other=board.getPiece(newPosition);
        if (other.getTeamColor() != piece.getTeamColor()) {
          move=new ChessMove(position, newPosition, null);
        }
      }
    }
    return move;
  }

  public ChessMove pawnCaptureLeft(ChessBoard board, ChessPiece piece, ChessPosition position){
    if ((newPosition.getColumn()) > 0) {
      if (board.getPiece(newPosition) != null) {
        ChessPiece other=board.getPiece(newPosition);
        if (other.getTeamColor() != piece.getTeamColor()) {
          move=new ChessMove(position, newPosition, null);
        }
      }
    }
    return move;
  }

  public ArrayList<ChessMove> promotion(ChessPosition position){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
    moveList.add(move);
    move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
    moveList.add(move);
    move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
    moveList.add(move);
    move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
    moveList.add(move);
    return moveList;
  }
}
