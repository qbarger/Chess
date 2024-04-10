package ui;

import chess.ChessGame;
import webSocketMessages.serverMessages.LoadGameMessage;

import javax.management.Notification;
import javax.websocket.Session;
import java.io.IOException;

public class GameHandler {
  ChessGame game;
  Session session;

  public void updateGame(ChessGame game){
    this.game = game;
  }

  public void loadGame(LoadGameMessage loadGameMessage){
    updateGame(loadGameMessage.game);

  }

  public void printMessage(String message){
    System.out.println(message);
  }

}
