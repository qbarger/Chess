package webSocketMessages.userCommands;

public class LeaveGameCommand {
  private int gameID;

  public LeaveGameCommand(int gameID){
    this.gameID = gameID;
  }
}
