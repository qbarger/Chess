package webSocketMessages.userCommands;

public class JoinObserverCommand {
  private int gameID;
  private String username;
  private String authtoken;

  public JoinObserverCommand(int gameID, String username, String authtoken){
    this.gameID = gameID;
    this.username = username;
    this.authtoken = authtoken;
  }

  public int getGameID(){return this.gameID;}
  public String getUsername(){return this.username;}
  public String getAuthtoken(){return this.authtoken;}
}
