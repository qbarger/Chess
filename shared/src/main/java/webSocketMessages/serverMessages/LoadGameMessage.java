package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
  public String message;
  public ChessGame game;

  public LoadGameMessage(String message, ChessGame game){
    super(ServerMessageType.LOAD_GAME);
    this.message = message;
    this.game = game;
  }

  public ChessGame getGame(){return this.game;}
}
