package service;

import chess.ChessGame;
import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryGameDao;
import dataAccess.memoryDAOs.MemoryUserDao;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {

  public ListGamesService listGamesService;
  public RegisterService registerService;
  public CreateGameService createGameService;
  public GameDao gameTestDB;
  public AuthDao authTestDB;
  public UserDao userTestDB;

  @BeforeEach
  void setup() throws DataAccessException {
    gameTestDB = new DatabaseGameDao();
    authTestDB = new DatabaseAuthDao();
    userTestDB = new DatabaseUserDao();
    listGamesService = new ListGamesService(authTestDB,gameTestDB);
    registerService = new RegisterService(userTestDB,authTestDB);
    createGameService = new CreateGameService(authTestDB,gameTestDB);
  }

  @Test
  void listGames() throws DataAccessException {
    gameTestDB.clear();
    authTestDB.clear();
    userTestDB.clear();
    AuthData authToken = registerService.register(new UserData("john","welovebears24","bear@gmail.com"));
    AuthData auth = new AuthData("john", authToken.authToken());
    CreateGameData gameName1 = new CreateGameData("new Game");
    GameID gameID1 = createGameService.createGame(auth.authToken(),gameName1);
    CreateGameData gameName2 = new CreateGameData("another Game");
    GameID gameID2 = createGameService.createGame(auth.authToken(),gameName2);
    CreateGameData gameName3 = new CreateGameData("third Game");
    GameID gameID3 = createGameService.createGame(auth.authToken(),gameName3);

    ArrayList<GameData> checkList = new ArrayList<>();
    GameData game1 = new GameData(1,null,null,"new Game",new ChessGame());
    GameData game2 = new GameData(2,null,null,"another Game",new ChessGame());
    GameData game3 = new GameData(3,null,null,"third Game",new ChessGame());
    checkList.add(game1);
    checkList.add(game2);
    checkList.add(game3);

    GameList gameList = listGamesService.listGames(authToken.authToken());
    assertEquals(checkList, gameList.games());
  }

  @Test
  void listGamesFails() throws DataAccessException{
    try {
      authTestDB.clear();
      gameTestDB.clear();
      userTestDB.clear();
      AuthData authToken = registerService.register(new UserData("john","welovebears24","bear@gmail.com"));
      AuthData auth = new AuthData("john", authToken.authToken());
      CreateGameData gameName = new CreateGameData("new Game");
      GameID gameID1 = createGameService.createGame(auth.authToken(),gameName);
      GameList gameList = listGamesService.listGames(authToken + "a");
      fail("Authorization not found.");
    }
    catch (DataAccessException error) {
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}