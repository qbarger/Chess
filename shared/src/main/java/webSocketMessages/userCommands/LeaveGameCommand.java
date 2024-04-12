package webSocketMessages.userCommands;

public class LeaveGameCommand extends UserGameCommand{
  private int gameID;
  private String username;
  private String authtoken;

  public LeaveGameCommand(int gameID, String username, String authtoken){
    super(authtoken);
    this.gameID = gameID;
    this.username = username;
    this.commandType = CommandType.LEAVE;
  }

  public String getUsername(){return this.username;}
  public int getGameID(){return this.gameID;}
  public String getAuthtoken(){return this.authtoken;}
}
