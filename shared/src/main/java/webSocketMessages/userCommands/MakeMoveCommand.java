package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
  private int gameID;
  private ChessMove move;
  private String username;
  private ChessGame.TeamColor color;

  public MakeMoveCommand(int gameID, ChessMove move, String username, String authtoken, ChessGame.TeamColor color){
    super(authtoken);
    this.gameID = gameID;
    this.move = move;
    this.username = username;
    this.commandType = CommandType.MAKE_MOVE;
    this.color = color;
  }

  public int getGameID(){return this.gameID;}
  public ChessMove getMove(){return this.move;}
  public String getUsername(){return this.username;}
  public ChessGame.TeamColor getColor(){return this.color;}
}
