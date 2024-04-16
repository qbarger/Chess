package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand{
  private ChessGame.TeamColor playerColor;
  private int gameID;
  private String username;

  public JoinPlayerCommand(String authtoken, ChessGame.TeamColor playerColor, int gameID, String username){
    super(authtoken);
    this.playerColor = playerColor;
    this.gameID = gameID;
    this.username = username;
    this.commandType = CommandType.JOIN_PLAYER;
  }

  public String getUsername(){return this.username;}
  public ChessGame.TeamColor getTeamColor(){return this.playerColor;}
  public int getGameID(){return this.gameID;}

  public void setUsername(String username){this.username = username;}
}
