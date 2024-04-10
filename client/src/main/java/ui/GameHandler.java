package ui;

import chess.ChessGame;

import javax.management.Notification;

public class GameHandler {
  ChessGame game;
  String message;

  public GameHandler(ChessGame game, String message){
    this.game = game;
    this.message = message;
  }

  public void updateGame(ChessGame game){

  }

  public void printMessage(String message){

  }

}
