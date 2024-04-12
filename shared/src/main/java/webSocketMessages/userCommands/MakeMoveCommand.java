package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
  private int gameID;
  private ChessMove move;
  private String username;
  private String authtoken;

  public MakeMoveCommand(int gameID, ChessMove move, String username, String authtoken){
    super(authtoken);
    this.gameID = gameID;
    this.move = move;
    this.username = username;
    this.commandType = CommandType.MAKE_MOVE;
  }

  public int getGameID(){return this.gameID;}
  public ChessMove getMove(){return this.move;}
  public String getUsername(){return this.username;}
  public String getAuthtoken(){return this.authtoken;}
}
