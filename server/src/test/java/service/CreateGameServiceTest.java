package service;

import chess.ChessGame;
import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryGameDao;
import dataAccess.memoryDAOs.MemoryUserDao;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {

  public CreateGameService createGameService;
  public RegisterService registerService;
  public UserDao userTestDB;
  public AuthDao authTestDB;
  public GameDao gameTestDB;

  @BeforeEach
  void setup() throws DataAccessException {
    userTestDB = new DatabaseUserDao();
    authTestDB = new DatabaseAuthDao();
    gameTestDB = new DatabaseGameDao();
    createGameService = new CreateGameService(authTestDB,gameTestDB);
    registerService = new RegisterService(userTestDB,authTestDB);
  }
  @Test
  void createGame() throws DataAccessException {
    userTestDB.clear();
    authTestDB.clear();
    gameTestDB.clear();
    AuthData authToken = registerService.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    CreateGameData gameName = new CreateGameData("My Game");
    GameID gameID = createGameService.createGame(authToken.authToken(), gameName);
    GameData checkGame = new GameData(1,null,null,"My Game",new ChessGame());

    GameData game = gameTestDB.getGame(gameID.gameID());
    assertEquals(checkGame, game);
  }

  @Test
  void createGameFails() throws DataAccessException {
    try {
      authTestDB.clear();
      userTestDB.clear();
      gameTestDB.clear();
      AuthData authToken=registerService.register(new UserData("qbarger", "johnnyland1", "kingkong@gmail.com"));
      CreateGameData gameName = new CreateGameData("My Game");
      GameID gameID=createGameService.createGame(authToken.authToken() + "a", gameName);
      fail("Authtoken not correct.");
    }
    catch (DataAccessException error) {
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}