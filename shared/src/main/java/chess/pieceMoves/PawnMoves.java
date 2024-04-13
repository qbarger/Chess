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

    /* Pawn is white */
    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
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
          if ((colLimit + 1) < 9) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, null);
                moveList.add(move);
              }
            }
          }
          newPosition=new ChessPosition(rowLimit, colLimit - 1);
          if ((colLimit - 1) > 0) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, null);
                moveList.add(move);
              }
            }
          }
        } else {
          /* Promotion */
          if (row == 2) {
            if (board.getPiece(newPosition) == null) {
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
              moveList.add(move);
            }
            /* Promotion Capture */
            newPosition=new ChessPosition(rowLimit, colLimit + 1);
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
                moveList.add(move);
              }
            }
            newPosition=new ChessPosition(rowLimit, colLimit - 1);
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
                moveList.add(move);
                move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
                moveList.add(move);
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
              if ((colLimit + 1) < 9) {
                if (board.getPiece(newPosition) != null) {
                  ChessPiece other=board.getPiece(newPosition);
                  if (other.getTeamColor() != piece.getTeamColor()) {
                    move=new ChessMove(position, newPosition, null);
                    moveList.add(move);
                  }
                }
              }
              newPosition=new ChessPosition(rowLimit, colLimit - 1);
              if ((colLimit - 1) > 0) {
                if (board.getPiece(newPosition) != null) {
                  ChessPiece other=board.getPiece(newPosition);
                  if (other.getTeamColor() != piece.getTeamColor()) {
                    move=new ChessMove(position, newPosition, null);
                    moveList.add(move);
                  }
                }
              }
            }
          }
        }
      }
    }

    /* Pawn is black */
    else {
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
          if ((colLimit + 1) < 9) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, null);
                moveList.add(move);
              }
            }
          }
          newPosition=new ChessPosition(rowLimit, colLimit - 1);
          if ((colLimit - 1) > 0) {
            if (board.getPiece(newPosition) != null) {
              ChessPiece other=board.getPiece(newPosition);
              if (other.getTeamColor() != piece.getTeamColor()) {
                move=new ChessMove(position, newPosition, null);
                moveList.add(move);
              }
            }
          }
        } else {
          /* Promotion */
          if (row == 7) {
            if (board.getPiece(newPosition) == null) {
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
              moveList.add(move);
              move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
              moveList.add(move);
            }
            /* Promotion Capture */
            newPosition=new ChessPosition(rowLimit, colLimit + 1);
            if (colLimit < 9) {
              if (board.getPiece(newPosition) != null) {
                ChessPiece other=board.getPiece(newPosition);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
                  moveList.add(move);
                }
              }
            }
            newPosition=new ChessPosition(rowLimit, colLimit - 1);
            if (colLimit > 0) {
              if (board.getPiece(newPosition) != null) {
                ChessPiece other=board.getPiece(newPosition);
                if (other.getTeamColor() != piece.getTeamColor()) {
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
                  moveList.add(move);
                  move=new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
                  moveList.add(move);
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
              if ((colLimit + 1) < 9) {
                if (board.getPiece(newPosition) != null) {
                  ChessPiece other=board.getPiece(newPosition);
                  if (other.getTeamColor() != piece.getTeamColor()) {
                    move=new ChessMove(position, newPosition, null);
                    moveList.add(move);
                  }
                }
              }
              newPosition=new ChessPosition(rowLimit, colLimit - 1);
              if ((colLimit - 1) > 0) {
                if (board.getPiece(newPosition) != null) {
                  ChessPiece other=board.getPiece(newPosition);
                  if (other.getTeamColor() != piece.getTeamColor()) {
                    move=new ChessMove(position, newPosition, null);
                    moveList.add(move);
                  }
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
