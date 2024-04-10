package webSocketMessages.userCommands;

public class LeaveGameCommand {
  private int gameID;
  private String username;

  public LeaveGameCommand(int gameID, String username){
    this.gameID = gameID;
    this.username = username;
  }
}
