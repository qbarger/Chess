package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {

  public CreateGameService testObject1;
  public RegisterService testObject2;
  public UserDao userTestDB;
  public AuthDao authTestDB;
  public GameDao gameTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    gameTestDB = new MemoryGameDao();
    testObject1 = new CreateGameService(authTestDB,gameTestDB);
    testObject2 = new RegisterService(userTestDB,authTestDB);
  }
  @Test
  void createGame() throws DataAccessException {
    AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    CreateGameData gameName = new CreateGameData("My Game");
    GameID gameID = testObject1.createGame(authToken.authToken(), gameName);
    GameData checkGame = new GameData(1,"","","My Game",new ChessGame());

    GameData game = gameTestDB.getGame(gameID.gameID());
    assertEquals(checkGame, game);
  }

  @Test
  void createGameFails() throws DataAccessException {
    try {
      AuthData authToken=testObject2.register(new UserData("qbarger", "johnnyland1", "kingkong@gmail.com"));
      CreateGameData gameName = new CreateGameData("My Game");
      GameID gameID=testObject1.createGame(authToken.authToken() + "a", gameName);
      fail("Authtoken not correct.");
    }
    catch (DataAccessException d) {
      assertEquals("Verification not found.", d.getMessage());
    }
  }
}