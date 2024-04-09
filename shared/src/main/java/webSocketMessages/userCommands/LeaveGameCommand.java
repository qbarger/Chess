package webSocketMessages.userCommands;

public class LeaveGameCommand {
  private int gameID;

  public LeaveGameCommand(int gameID, String username){
    this.gameID = gameID;
  }
}
