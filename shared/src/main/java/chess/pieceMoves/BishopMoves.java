package chess.pieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMoves {
  private ChessMove move;
  private ChessPosition newPosition;

  private boolean stop;

  public ArrayList<ChessMove> getBishopMoves(ChessPosition position, ChessBoard board){
    ArrayList<ChessMove> moveList = new ArrayList<>();
    ChessPiece piece = board.getPiece(position);
    int row =position.getRow();
    int col =position.getColumn();

    /* Upper right */
    int rowLimit = row + 1;
    int colLimit = col + 1;
    while(rowLimit < 9 && colLimit < 9){
      moveList = addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit++;
      colLimit++;
    }

    /* Upper left */
    rowLimit = row + 1;
    colLimit = col - 1;
    while(rowLimit < 9 && colLimit > 0){
      moveList = addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit++;
      colLimit--;
    }

    /* Lower Left */
    rowLimit = row - 1;
    colLimit = col - 1;
    while(rowLimit > 0 && colLimit > 0){
      moveList = addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit--;
      colLimit--;
    }

    /* Lower right */
    rowLimit = row - 1;
    colLimit = col + 1;
    while(rowLimit > 0 && colLimit < 9){
      moveList = addMove(rowLimit, colLimit, piece, position, board, moveList);
      if(this.stop){break;}
      rowLimit--;
      colLimit++;
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
