package webSocketMessages.serverMessages;

public class LoadGameMessage extends ServerMessage {
  public String message;

  public LoadGameMessage(String message){
    super(ServerMessageType.LOAD_GAME);
    this.message = message;
  }
}
