package webSocketMessages.userCommands;

public class ResignGameCommand {
  private int gameID;
  private String username;
  private String authtoken;

  public ResignGameCommand(int gameID, String username, String authtoken){
    this.gameID = gameID;
    this.username = username;
    this.authtoken = authtoken;
  }

  public String getUsername(){return this.username;}
  public int getGameID(){return this.gameID;}
  public String getAuthtoken(){return this.authtoken;}
}
