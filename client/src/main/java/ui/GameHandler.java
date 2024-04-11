package ui;

import chess.ChessGame;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;

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

  public void printMessage(NotificationMessage message){
    System.out.println();
    System.out.println();
    System.out.println(message.getMessage());
  }

}
