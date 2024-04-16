package ui;

import chess.ChessGame;
import chess.ResponseException;
import dataAccess.DatabaseGameDao;
import dataAccess.GameDao;
import model.*;

import java.util.Scanner;

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

      ChessGame.TeamColor color;
      if(teamColor.equals("WHITE")){
        color = ChessGame.TeamColor.WHITE;
      } else {
        color = ChessGame.TeamColor.BLACK;
      }

      GameDao gameDao = new DatabaseGameDao();
      GameList gameList = gameDao.listGames();
      GameData gameData = gameList.getGame(gameID);
      JoinGameData joinGameData=new JoinGameData(teamColor, gameData.gameID());
      var path="/game";
      serverFacade.makeRequest("PUT", auth.authToken(), path, joinGameData, null);

      GameHandler gameHandler = new GameHandler();
      WebsocketFacade websocketFacade = new WebsocketFacade(serverUrl, gameHandler);
      websocketFacade.joinPlayer(auth.username(), gameData.gameID(), color, auth.authToken());
      Gameplay gameplay = new Gameplay(websocketFacade, gameData.gameID(), auth.username(), auth.authToken(), null, color);
      gameplay.run();
      //makeBoardTop();
      //makeBoardBottom();
    } catch (ResponseException exception){
      System.err.println("Cannot join game. Please enter valid game...");
    }
  }

  public void observe(AuthData auth) throws Exception {
    try {
      Scanner scanner=new Scanner(System.in);
      System.out.println("Enter Game ID:");
      int gameID=Integer.parseInt(scanner.next());

      GameDao gameDao = new DatabaseGameDao();
      GameList gameList = gameDao.listGames();
      GameData gameData = gameList.getGame(gameID);
      JoinGameData joinGameData=new JoinGameData(null, gameID);
      var path="/game";
      serverFacade.makeRequest("PUT", auth.authToken(), path, joinGameData, null);

      System.out.println("Joining game as observer...");
      System.out.println();

      GameHandler gameHandler = new GameHandler();
      WebsocketFacade websocketFacade = new WebsocketFacade(serverUrl, gameHandler);
      websocketFacade.joinObserver(gameData.gameID(), auth.username(), auth.authToken());
      Gameplay gameplay = new Gameplay(websocketFacade, gameData.gameID(), auth.username(), auth.authToken(), null, null);
      gameplay.run();
      //makeBoardTop();
      //makeBoardBottom();
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
}
