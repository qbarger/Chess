package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand {
  private String authtoken;
  private ChessGame.TeamColor teamColor;
  private int gameID;
  private String username;

  public JoinPlayerCommand(String authtoken, ChessGame.TeamColor teamColor, int gameID, String username){
    this.authtoken = authtoken;
    this.teamColor = teamColor;
    this.gameID = gameID;
    this.username = username;
  }

  public String getUsername(){return this.username;}
  public String getAuthtoken(){return this.authtoken;}
  public ChessGame.TeamColor getTeamColor(){return this.teamColor;}
  public int getGameID(){return this.gameID;}
}
