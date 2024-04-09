package webSocketMessages.userCommands;

public class JoinObserverCommand {
  private int gameID;

  public JoinObserverCommand(int gameID){
    this.gameID = gameID;
  }
}
