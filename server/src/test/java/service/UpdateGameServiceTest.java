package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateGameServiceTest {

  public UpdateGameService testObject1;
  public RegisterService testObject2;
  public CreateGameService testObject3;
  public GameDao gameTestDB;
  public AuthDao authTestDB;
  public UserDao userTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    gameTestDB = new MemoryGameDao();
    testObject1 = new UpdateGameService(authTestDB,gameTestDB);
    testObject2 = new RegisterService(userTestDB,authTestDB);
    testObject3 = new CreateGameService(authTestDB,gameTestDB);
  }

  @Test
  void joinGame() throws DataAccessException {
    AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    AuthData auth = new AuthData("qbarger", authToken.authToken());
    CreateGameData gamename = new CreateGameData("My Game");
    GameID gameID = testObject3.createGame(auth.authToken(), gamename);
    GameData newGame = new GameData(gameID.gameID(),null,"qbarger","My Game",new ChessGame());
    JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
    testObject1.joinGame(add,authToken.authToken());

    assertEquals(newGame, new GameData(gameID.gameID(),null,"qbarger","My Game",new ChessGame()));
  }

  @Test
  void joinGameFails() throws DataAccessException {
    try {
      AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
      AuthData auth = new AuthData("qbarger", authToken.authToken());
      CreateGameData gamename = new CreateGameData("My Game");
      GameID gameID = testObject3.createGame(auth.authToken(), gamename);
      GameData newGame = new GameData(gameID.gameID(),"","qbarger","My Game",new ChessGame());
      JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
      testObject1.joinGame(add,authToken.authToken() + "b");
      fail("Expected to throw a Data Access Exception.");
    }
    catch (DataAccessException d) {
      assertEquals("Error: unauthorized", d.getMessage());
    }
  }
}