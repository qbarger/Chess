package chess.pieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMoves {
  private BishopMoves bishop;
  private RookMoves rook;

  public ArrayList<ChessMove> getQueenMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ArrayList<ChessMove> diagonalList;
    ArrayList<ChessMove> straightList;
    bishop = new BishopMoves();
    rook = new RookMoves();

    diagonalList = bishop.getBishopMoves(position,board);
    straightList = rook.getRookMoves(position,board);

    for(ChessMove move:diagonalList){
      moveList.add(move);
    }
    for(ChessMove move:straightList){
      moveList.add(move);
    }

    return moveList;
  }
}
