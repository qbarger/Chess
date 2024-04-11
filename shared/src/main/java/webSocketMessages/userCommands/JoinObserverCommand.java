package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand{
  private int gameID;
  private String username;
  private String authtoken;

  public JoinObserverCommand(int gameID, String username, String authtoken){
    super(authtoken);
    this.gameID = gameID;
    this.username = username;
    this.authtoken = authtoken;
    this.commandType = CommandType.JOIN_OBSERVER;
  }

  public int getGameID(){return this.gameID;}
  public String getUsername(){return this.username;}
  public String getAuthtoken(){return this.authtoken;}
}
