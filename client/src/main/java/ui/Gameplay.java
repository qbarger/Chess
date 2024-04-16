package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ResponseException;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class Gameplay {
  WebsocketFacade websocketFacade;
  int gameID;
  String username;
  String authtoken;
  ChessMove move;
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
    System.out.println("move - make move");
    System.out.println("resign - from the game");
    System.out.println("highlight - legal moves");
    System.out.println("help - with commands");
  }

  public void redrawChessBoard() {
    websocketFacade.gameHandler.printBoard(websocketFacade.gameHandler.game, color);
  }

  public void leave() throws ResponseException, IOException {
    System.out.println("Leaving game...");
    websocketFacade.leaveGame(gameID, username, authtoken);
    playing = false;
  }

  public void makeMove() throws ResponseException, IOException {
    ChessGame chessGame = websocketFacade.gameHandler.game;
    ChessGame.TeamColor teamColor = chessGame.getTeamTurn();
    if(teamColor != color){
      System.out.println("It is not your turn. Wait for opponent to make move...");
      return;
    }
    int row, col;
    Scanner scanner=new Scanner(System.in);
    System.out.println("Which piece do you want to move?");
    System.out.print("Enter the letter of the piece's square (ex. 'A'): ");
    String input=scanner.next();

    if (input.equals("A")) {
      col=8;
    } else if (input.equals("B")) {
      col=7;
    } else if (input.equals("C")) {
      col=6;
    } else if (input.equals("D")) {
      col=5;
    } else if (input.equals("E")) {
      col=4;
    } else if (input.equals("F")) {
      col=3;
    } else if (input.equals("G")) {
      col=2;
    } else if (input.equals("H")) {
      col=1;
    } else {
      invalidInput();
      return;
    }
    System.out.println("Enter the number of the piece's square: ");
    String number=scanner.next();
    if (Integer.parseInt(number) == 1) {
      row=1;
    } else if (Integer.parseInt(number) == 2) {
      row=2;
    } else if (Integer.parseInt(number) == 3) {
      row=3;
    } else if (Integer.parseInt(number) == 4) {
      row=4;
    } else if (Integer.parseInt(number) == 4) {
      row=4;
    } else if (Integer.parseInt(number) == 5) {
      row=5;
    } else if (Integer.parseInt(number) == 6) {
      row=6;
    } else if (Integer.parseInt(number) == 7) {
      row=7;
    } else if (Integer.parseInt(number) == 8) {
      row=8;
    } else {
      invalidInput();
      return;
    }
    ChessGame game = websocketFacade.gameHandler.game;
    Collection<ChessMove> moves = game.validMoves(new ChessPosition(row, col));

    System.out.println("Here are the possible moves...");
    int i = 0;
    for(ChessMove move : moves){
      if(move.getEndPosition().getColumn() == 8){
        System.out.println(i + ") A" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 7){
        System.out.println(i + ") B" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 6){
        System.out.println(i + ") C" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 5){
        System.out.println(i + ") D" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 4){
        System.out.println(i + ") E" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 3){
        System.out.println(i + ") F" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 2){
        System.out.println(i + ") G" + move.getEndPosition().getRow());
      } else if (move.getEndPosition().getColumn() == 1){
        System.out.println(i + ") H" + move.getEndPosition().getRow());
      }
      i++;
    }
    System.out.println();
    System.out.print("Select the number of a move: ");
    String select =scanner.next();

    int j = 0;
    for(ChessMove move : moves){
      if(Integer.toString(j).equals(select)){
        System.out.println("Making move...");
        websocketFacade.makeMove(gameID, move, username, authtoken, color);
      }
      j++;
    }
    websocketFacade.gameHandler.updateGame(websocketFacade.gameHandler.game);
  }

  public void invalidInput(){
    System.out.println("Invalid input...");
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
    } else if (userInput.equals("move")) {
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
