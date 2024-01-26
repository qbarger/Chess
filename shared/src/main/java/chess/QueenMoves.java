package chess;

import java.util.ArrayList;

public class QueenMoves {
  private ChessMove move;
  private ChessPosition new_position;
  private BishopMoves bishop;
  private RookMoves rook;

  public ArrayList<ChessMove> getQueenMoves(ChessPosition position, ChessBoard board) {
    ArrayList<ChessMove> moveList=new ArrayList<>();
    ArrayList<ChessMove> diagonalList=new ArrayList<>();
    ArrayList<ChessMove> straightList=new ArrayList<>();
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
