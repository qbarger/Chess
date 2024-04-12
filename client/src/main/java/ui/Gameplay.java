package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ResponseException;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import service.UpdateGameService;

import java.io.IOException;
import java.util.Scanner;

public class Gameplay {
  WebsocketFacade websocketFacade;
  int gameID;
  String username;
  String authtoken;
  ChessMove move;
  GameHandler gameHandler;
  ChessGame.TeamColor color;
  private boolean playing;


  public Gameplay(WebsocketFacade websocketFacade, int gameID, String username, String authtoken, ChessMove move, ChessGame.TeamColor color){
    this.websocketFacade = websocketFacade;
    this.gameID = gameID;
    this.username = username;
    this.authtoken = authtoken;
    this.move = move;
    this.color = color;
    playing = true;
  }

  public void help(){
    System.out.println("redraw - board");
    System.out.println("leave - game");
    System.out.println("make move");
    System.out.println("resign - from the game");
    System.out.println("highlight - legal moves");
    System.out.println("help - with commands");
  }

  public void redrawChessBoard() throws DataAccessException {
    websocketFacade.gameHandler.printBoard(websocketFacade.gameHandler.game, color);
  }

  public void leave() throws ResponseException, IOException {
    websocketFacade.leaveGame(gameID, username, authtoken);
    playing = false;
  }

  public void makeMove() throws ResponseException, IOException {
    websocketFacade.makeMove(gameID, move, username, authtoken, color);
  }

  public void resign() throws ResponseException, IOException {
    websocketFacade.resignGame(gameID, username, authtoken);
    playing = false;
  }

  public void highlightLegalMoves(){
    System.out.println("highlighting...");
  }

  public void run() throws Exception {
    System.out.println("Entering gameplay...");
    System.out.println();
    help();

    Scanner scanner = new Scanner(System.in);
    String userInput;

    while(playing){
      System.out.printf("[LOGGED_IN] >>> ");
      userInput=scanner.next();

      eval(userInput);
    }
  }

  public String eval(String userInput) throws Exception {
    if (userInput.equals("redraw")) {
      redrawChessBoard();
    } else if (userInput.equals("leave")) {
      leave();
    } else if (userInput.equals("make move")) {
      makeMove();
    } else if (userInput.equals("resign")) {
      resign();
    } else if (userInput.equals("highlight")) {
      highlightLegalMoves();
    } else if (userInput.equals("help")) {
      help();
    } else {
      System.out.println("Invalid input...");
      return "";
    }
    return "";
  }
}
