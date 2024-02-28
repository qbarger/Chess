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
    AuthData authToken = testObject2.register(new UserData("john","welovebears24","bear@gmail.com"));
    AuthData auth = new AuthData("john", authToken.authToken());
    CreateGameData gamename1 = new CreateGameData("new Game");
    GameID gameID1 = testObject3.createGame(auth.authToken(),gamename1);
    CreateGameData gamename2 = new CreateGameData("another Game");
    GameID gameID2 = testObject3.createGame(auth.authToken(),gamename2);
    CreateGameData gamename3 = new CreateGameData("third Game");
    GameID gameID3 = testObject3.createGame(auth.authToken(),gamename3);

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
      AuthData authToken = testObject2.register(new UserData("john","welovebears24","bear@gmail.com"));
      AuthData auth = new AuthData("john", authToken.authToken());
      CreateGameData gamename = new CreateGameData("new Game");
      GameID gameID1 = testObject3.createGame(auth.authToken(),gamename);
      GameList gameList = testObject1.listGames(new AuthData("john", authToken + "a"));
      fail("Authorization not found.");
    }
    catch (DataAccessException d) {
      assertEquals("Authorization not found.", d.getMessage());
    }
  }
}