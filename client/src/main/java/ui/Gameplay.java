package ui;

import chess.ChessMove;
import chess.ResponseException;
import model.AuthData;

import java.io.IOException;

public class Gameplay {
  WebsocketFacade websocketFacade;
  int gameID;
  String username;
  String authtoken;
  ChessMove move;


  public Gameplay(WebsocketFacade websocketFacade, int gameID, String username, String authtoken, ChessMove move){
    this.websocketFacade = websocketFacade;
    this.gameID = gameID;
    this.username = username;
    this.authtoken = authtoken;
    this.move = move;
  }

  public void help(){
    System.out.println("redraw - board");
    System.out.println("leave - game");
    System.out.println("make move");
    System.out.println("resign - from the game");
    System.out.println("highlight - legal moves");
  }

  public void redrawChessBoard(){
    System.out.println("drawing...");
  }

  public void leave() throws ResponseException, IOException {
    websocketFacade.leaveGame(gameID, username, authtoken);
  }

  public void makeMove() throws ResponseException, IOException {
    websocketFacade.makeMove(gameID, move, username, authtoken);
  }

  public void resign() throws ResponseException, IOException {
    websocketFacade.resignGame(gameID, username, authtoken);
  }

  public void highlightLegalMoves(){
    System.out.println("highlighting...");
  }

  public void run(String choice){
    System.out.println("Entering gameplay...");

  }
}
