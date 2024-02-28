package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {

  public ListGamesService testObject1;
  public RegisterService testObject2;
  public CreateGameService testObject3;
  public GameDao gameTestDB;
  public AuthDao authTestDB;
  public UserDao userTestDB;

  @BeforeEach
  void setup(){
    gameTestDB = new MemoryGameDao();
    authTestDB = new MemoryAuthDao();
    userTestDB = new MemoryUserDao();
    testObject1 = new ListGamesService(authTestDB,gameTestDB);
    testObject2 = new RegisterService(userTestDB,authTestDB);
    testObject3 = new CreateGameService(authTestDB,gameTestDB);
  }

  @Test
  void listGames() throws DataAccessException {
    String authToken = testObject2.register(new UserData("john","welovebears24","bear@gmail.com"));
    AuthData auth = new AuthData(authToken,"john");
    GameID gameID1 = testObject3.createGame(auth,"new Game");
    GameID gameID2 = testObject3.createGame(auth,"another Game");
    GameID gameID3 = testObject3.createGame(auth,"third Game");

    ArrayList<GameData> checkList = new ArrayList<>();
    GameData game1 = new GameData(1,"","","new Game",new ChessGame());
    GameData game2 = new GameData(2,"","","another Game",new ChessGame());
    GameData game3 = new GameData(3,"","","third Game",new ChessGame());
    checkList.add(game2);
    checkList.add(game3);
    checkList.add(game1);

    GameList gameList = testObject1.listGames(auth);
    assertEquals(checkList, gameList.gameList());
  }

  @Test
  void listGamesFails() throws DataAccessException{
    try {
      String authToken = testObject2.register(new UserData("john","welovebears24","bear@gmail.com"));
      AuthData auth = new AuthData(authToken,"john");
      GameID gameID1 = testObject3.createGame(auth,"new Game");
      GameList gameList = testObject1.listGames(new AuthData(authToken + "a","john"));
      fail("Authorization not found.");
    }
    catch (DataAccessException d) {
      assertEquals("Authorization not found.", d.getMessage());
    }
  }
}