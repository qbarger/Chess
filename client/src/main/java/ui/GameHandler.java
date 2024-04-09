package ui;

import chess.ChessGame;

import javax.management.Notification;

public interface GameHandler {

  public void updateGame(ChessGame game);

  public void printMessage(String message);

}
