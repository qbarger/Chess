package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand {
  private String authtoken;
  private ChessGame.TeamColor teamColor;
  private int gameID;

  public JoinPlayerCommand(String authtoken, ChessGame.TeamColor teamColor, int gameID, String username){
    this.authtoken = authtoken;
    this.teamColor = teamColor;
    this.gameID = gameID;
  }
}
