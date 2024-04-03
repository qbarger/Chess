package ui;

import chess.ResponseException;
import dataAccess.DatabaseGameDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import model.*;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Postlogin {

  private final String serverUrl;
  private boolean playing;
  ServerFacade serverFacade;

  public Postlogin(String url){
    playing = true;
    this.serverUrl = url;
    serverFacade = new ServerFacade(serverUrl);
  }

  public void help(){
    System.out.println("create <NAME> - a game");
    System.out.println("list - games");
    System.out.println("join <ID> [WHITE|BLACK|<empty>] - a game");
    System.out.println("observe <ID> - a game");
    System.out.println("logout - when you are done");
    System.out.println("quit - playing chess");
    System.out.println("help - with possible commands");
  }

  public void logout(AuthData auth) throws Exception {
    try {
      System.out.println("Logging out...");

      var path="/session";
      serverFacade.makeRequest("DELETE", auth.authToken(), path, null, Object.class);
      playing=false;
    } catch (Exception e){
      System.err.println("Cannot logout. Please quit the program...");
    }
  }

  public void create(AuthData auth) throws Exception {
    try {
      Scanner scanner=new Scanner(System.in);
      System.out.println("Enter the name of the game:");
      String gameName=scanner.nextLine();
      CreateGameData gameData=new CreateGameData(gameName);

      var path="/game";
      GameID gameID=serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);
    } catch (Exception e){
      System.err.println("Cannot create game...");
    }
  }

  public void list(AuthData auth) throws Exception {
    try {
      System.out.println("Fetching games...");
      System.out.print("\n");

      var path="/game";
      GameList game=serverFacade.makeRequest("GET", auth.authToken(), path, null, GameList.class);
      int index=0;
      for (GameData gameData : game.games()) {
        System.out.println("Game ID: " + index);
        System.out.println("Game Name: " + gameData.gameName());
        System.out.println("White: " + gameData.whiteUsername());
        System.out.println("Black: " + gameData.blackUsername());
        System.out.print("\n");
        index++;
      }
    } catch (Exception e){
      System.err.println("Cannot list games...");
    }

  }

  public void join(AuthData auth) throws Exception {
    try {
      Scanner scanner=new Scanner(System.in);
      System.out.print("Enter Game ID:");
      int gameID=Integer.parseInt(scanner.next());
      System.out.print("Enter your team color [type BLACK or WHITE]:");
      String teamColor=scanner.next();

      GameDao gameDao = new DatabaseGameDao();
      GameList gameList = gameDao.listGames();
      GameData gameData = gameList.getGame(gameID);
      JoinGameData joinGameData=new JoinGameData(teamColor, gameData.gameID());
      var path="/game";
      serverFacade.makeRequest("PUT", auth.authToken(), path, joinGameData, null);

      makeBoardTop();
      makeBoardBottom();
    } catch (ResponseException exception){
      System.err.println("Cannot join game. Please enter valid game...");
    }
  }

  public void observe(AuthData auth) throws Exception {
    try {
      Scanner scanner=new Scanner(System.in);
      System.out.println("Enter Game ID:");
      int gameID=Integer.parseInt(scanner.next());

      JoinGameData joinGameData=new JoinGameData(null, gameID);
      var path="/game";
      serverFacade.makeRequest("PUT", auth.authToken(), path, joinGameData, null);

      System.out.println("Joining game as observer...");
      System.out.println();

      makeBoardTop();
      makeBoardBottom();
    } catch (Exception e){
      System.err.println("Cannot observe game. Please enter valid game...");
    }
  }

  public void run(AuthData auth) throws Exception{
    System.out.println("Logged in as " + auth.username() + "...");

    Scanner scanner = new Scanner(System.in);
    String userInput;

    while(playing) {
      System.out.printf("[LOGGED_IN] >>> ");
      userInput=scanner.next();

      if(userInput.equals("quit")){
        logout(auth);
        System.out.println("Quitting...");
        System.exit(0);
      }
      else {
        eval(userInput, auth);
      }
    }
  }

  public String eval(String userInput, AuthData auth) throws Exception {
    if (userInput.equals("create")) {
      create(auth);
    } else if (userInput.equals("list")) {
      list(auth);
    } else if (userInput.equals("join")) {
      join(auth);
    } else if (userInput.equals("observe")) {
      observe(auth);
    } else if (userInput.equals("logout")) {
      logout(auth);
    } else if (userInput.equals("help")) {
      help();
    } else {
      System.out.println("Invalid input...");
      return "";
    }
    return "";
  }

  public void makeBoardTop(){
    int size = 10;
    String space;
    String piece="";
    for(int row = 0; row < size; row++){
      for(int col = 0; col < size; col++){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
        }
        else {
          space = SET_BG_COLOR_LIGHT_GREY;
        }
        System.out.print(space);
        if(col == 0 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if (col == 9 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if(row == 0 && col != 0 && col != 9){
          piece = "";
          printLettersTop(col);
        } else if(row == 1){
          piece = "";
          printPiecesWhiteTop(col);
        } else if (row == 2){
          piece = WHITE_PAWN;
        } else if (row == 7) {
          piece = BLACK_PAWN;
        } else if (row == 8) {
          piece = "";
          printPiecesBlackBottom(col);
        } else if (row == 9 && col != 0 && col != 9) {
          piece = "";
          printLettersBottom(col);
        } else {
          piece = EMPTY;
        }

        System.out.print(piece);
        space= RESET_BG_COLOR;
      }
      space = RESET_BG_COLOR;
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void makeBoardBottom(){
    int size = 10;
    String space = "";
    String piece = "";

    for(int row = size - 1; row >= 0; row--){
      for(int col = size - 1; col >= 0; col--){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
        }
        else {
          space = SET_BG_COLOR_LIGHT_GREY;
        }
        System.out.print(space);
        if(col == 0 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if (col == 9 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if(row == 0 && col != 0 && col != 9){
          piece = "";
          printLettersTop(col);
        } else if(row == 1){
          piece = "";
          printPiecesWhiteTop(col);
        } else if (row == 2){
          piece = WHITE_PAWN;
        } else if (row == 7) {
          piece = BLACK_PAWN;
        } else if (row == 8) {
          piece = "";
          printPiecesBlackBottom(col);
        } else if (row == 9 && col != 0 && col != 9) {
          piece = "";
          printLettersBottom(col);
        } else {
          piece = EMPTY;
        }

        System.out.print(piece);
        space= RESET_BG_COLOR;
      }
      space = RESET_BG_COLOR;
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void printNumbers(int row){
    String number = EMPTY;
    if(row == 1){
      number = " 1 ";
    } else if (row == 2) {
      number = " 2 ";
    } else if (row == 3){
      number = " 3 ";
    } else if (row == 4) {
      number = " 4 ";
    } else if (row == 5){
      number = " 5 ";
    } else if (row == 6) {
      number = " 6 ";
    } else if (row == 7) {
      number = " 7 ";
    } else if (row == 8) {
      number = " 8 ";
    }
    System.out.print(number);
  }

  public void printLettersTop(int col){
    String letter = EMPTY;
    if(col == 1) {
      letter=" H ";
    } else if(col == 2){
      letter=" G ";
    } else if (col == 3) {
      letter=" F ";
    } else if (col == 4) {
      letter=" E ";
    } else if (col == 5) {
      letter=" D ";
    } else if (col == 6) {
      letter=" C ";
    } else if (col == 7){
      letter=" B ";
    } else if (col == 8) {
      letter=" A ";
    }
    System.out.print(letter);
  }

  public void printLettersBottom(int col){
    String letter = EMPTY;
    if(col == 1) {
      letter=" H ";
    } else if(col == 2){
      letter=" G ";
    } else if (col == 3) {
      letter=" F ";
    } else if (col == 4) {
      letter=" E ";
    } else if (col == 5) {
      letter=" D ";
    } else if (col == 6) {
      letter=" C ";
    } else if (col == 7){
      letter=" B ";
    } else if (col == 8) {
      letter=" A ";
    }
    System.out.print(letter);
  }

  public void printPiecesWhiteTop(int col){
    String piece = "";
    if(col == 1) {
      piece=WHITE_ROOK;
    } else if(col == 2){
      piece = WHITE_KNIGHT;
    } else if (col == 3) {
      piece = WHITE_BISHOP;
    } else if (col == 4) {
      piece = WHITE_KING;
    } else if (col == 5) {
      piece = WHITE_QUEEN;
    } else if (col == 6) {
      piece = WHITE_BISHOP;
    } else if (col == 7){
      piece = WHITE_KNIGHT;
    } else if (col == 8) {
      piece = WHITE_ROOK;
    }
    System.out.print(piece);
  }

  public void printPiecesBlackBottom(int col){
    String piece = "";
    if(col == 1) {
      piece=BLACK_ROOK;
    } else if(col == 2){
      piece = BLACK_KNIGHT;
    } else if (col == 3) {
      piece = BLACK_BISHOP;
    } else if (col == 4) {
      piece = BLACK_KING;
    } else if (col == 5) {
      piece = BLACK_QUEEN;
    } else if (col == 6) {
      piece = BLACK_BISHOP;
    } else if (col == 7){
      piece = BLACK_KNIGHT;
    } else if (col == 8) {
      piece = BLACK_ROOK;
    }
    System.out.print(piece);
  }
}
