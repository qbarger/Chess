package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand {
  private int gameID;
  private ChessMove move;

  public MakeMoveCommand(int gameID, ChessMove move){
    this.gameID = gameID;
    this.move = move;
  }
}
