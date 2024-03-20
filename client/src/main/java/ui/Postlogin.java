package ui;

import chess.ResponseException;
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
    System.out.println("Logging out...");

    var path = "/session";
    serverFacade.makeRequest("DELETE", auth.authToken(), path, null, Object.class);
    playing = false;
  }

  public void create(AuthData auth) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the name of the game:");
    String gameName = scanner.nextLine();
    CreateGameData gameData = new CreateGameData(gameName);

    var path = "/game";
    GameID gameID = serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);
  }

  public void list(AuthData auth) throws Exception {
    System.out.println("Fetching games...");

    var path = "/game";
    GameList game = serverFacade.makeRequest("GET", auth.authToken(), path, null, GameList.class);
    for (GameData gameData: game.games()) {
      System.out.print(gameData.toString());
    }

  }

  public void join(){

  }

  public void observe(){

  }

  public void run(AuthData auth) throws Exception{

    Scanner user = new Scanner(System.in);
    String userInput;

    while(playing) {
      System.out.printf("[LOGGED_IN] >>> ");
      userInput=user.next();

      if(userInput.equals("quit")){
        System.out.println("Quitting...");
        System.exit(0);
      }
      else {
        eval(userInput, auth);
      }
    }
    user.close();
  }

  public String eval(String userInput, AuthData auth) throws Exception {
    if (userInput.equals("create")) {
      create(auth);
    } else if (userInput.equals("list")) {
      list(auth);
    } else if (userInput.equals("join")) {
      join();
    } else if (userInput.equals("observe")) {
      observe();
    } else if (userInput.equals("logout")) {
      logout(auth);
    } else if (userInput.equals("help")) {
      help();
    }
    return "";
  }
}
