package webSocketMessages.userCommands;

public class ResignGameCommand {
  private int gameID;

  public ResignGameCommand(int gameID, String username){
    this.gameID = gameID;
  }
}
