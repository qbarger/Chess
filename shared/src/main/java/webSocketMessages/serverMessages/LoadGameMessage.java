package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
  //public String message;
  public ChessGame game;
  public ChessGame.TeamColor color;

  public LoadGameMessage(ChessGame game, ChessGame.TeamColor color){
    super(ServerMessageType.LOAD_GAME);
    //this.message = message;
    this.game = game;
    this.color = color;
  }

  public ChessGame getGame(){return this.game;}
}
