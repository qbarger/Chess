package webSocketMessages.userCommands;

public class ResignGameCommand extends UserGameCommand{
  private int gameID;
  private String username;
  private String authtoken;

  public ResignGameCommand(int gameID, String username, String authtoken){
    super(authtoken);
    this.gameID = gameID;
    this.username = username;
    this.commandType = CommandType.RESIGN;
  }

  public String getUsername(){return this.username;}
  public int getGameID(){return this.gameID;}
  public String getAuthtoken(){return this.authtoken;}
}
