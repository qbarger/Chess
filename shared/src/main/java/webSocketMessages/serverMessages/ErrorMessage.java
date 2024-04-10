package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
  String message;
  public ErrorMessage(String message) {
    super(ServerMessageType.ERROR);
    this.message = message;
  }
}
