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

class UpdateGameServiceTest {

  public UpdateGameService updateGameService;
  public RegisterService registerService;
  public CreateGameService createGameService;
  public GameDao gameTestDB;
  public AuthDao authTestDB;
  public UserDao userTestDB;

  @BeforeEach
  void setup() throws DataAccessException {
    userTestDB = new DatabaseUserDao();
    authTestDB = new DatabaseAuthDao();
    gameTestDB = new DatabaseGameDao();
    updateGameService = new UpdateGameService(authTestDB,gameTestDB);
    registerService = new RegisterService(userTestDB,authTestDB);
    createGameService = new CreateGameService(authTestDB,gameTestDB);
  }

  @Test
  void joinGame() throws DataAccessException {
    userTestDB.clear();
    authTestDB.clear();
    gameTestDB.clear();
    AuthData authToken = registerService.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    AuthData auth = new AuthData("qbarger", authToken.authToken());
    CreateGameData gameName = new CreateGameData("My Game");
    GameID gameID = createGameService.createGame(auth.authToken(), gameName);
    GameData newGame = new GameData(gameID.gameID(),null,"qbarger","My Game",new ChessGame());
    JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
    updateGameService.joinGame(add,authToken.authToken());

    assertEquals(newGame, new GameData(gameID.gameID(),null,"qbarger","My Game",new ChessGame()));
  }

  @Test
  void joinGameFails() throws DataAccessException {
    try {
      authTestDB.clear();
      userTestDB.clear();
      gameTestDB.clear();
      AuthData authToken = registerService.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
      AuthData auth = new AuthData("qbarger", authToken.authToken());
      CreateGameData gameName = new CreateGameData("My Game");
      GameID gameID = createGameService.createGame(auth.authToken(), gameName);
      GameData newGame = new GameData(gameID.gameID(),"","qbarger","My Game",new ChessGame());
      JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
      updateGameService.joinGame(add,authToken.authToken() + "b");
      fail("Expected to throw a Data Access Exception.");
    }
    catch (DataAccessException error) {
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}