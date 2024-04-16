package chess.pieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMoves {
  private ChessMove move;
  private ChessPosition newPosition;
  private boolean stop;

  public ArrayList<ChessMove> getRookMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ChessPiece piece=board.getPiece(position);
    int row=position.getRow();
    int col=position.getColumn();

    /* Up */
    int rowLimit = row + 1;
    int colLimit = col;

    while(rowLimit < 9){
      addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit++;
    }

    /* Down */
    rowLimit = row - 1;
    colLimit = col;

    while(rowLimit > 0){
      addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit--;
    }

    /* Right */
    rowLimit = row;
    colLimit = col + 1;

    while(colLimit < 9){
      addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      colLimit++;
    }

    /* Left */
    rowLimit = row;
    colLimit = col - 1;

    while(colLimit > 0){
      addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      colLimit--;
    }

    return moveList;
  }

  private ArrayList<ChessMove> addMove(int rowLimit, int colLimit, ChessPiece piece, ChessPosition position, ChessBoard board, ArrayList<ChessMove> moveList){
    this.stop = false;
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
        this.stop = true;
      }
      else {
        this.stop = true;
      }
    }
    return moveList;
  }
}
