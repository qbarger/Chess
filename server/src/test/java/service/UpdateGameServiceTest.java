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
  void updateGame() throws DataAccessException{
    AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    AuthData auth = new AuthData("qbarger", authToken.authToken());
    CreateGameData gamename = new CreateGameData("My Game");
    GameID gameID = testObject3.createGame(auth,gamename);
    GameData newGame = new GameData(gameID.gameID(),"Jeff","John","My Game",new ChessGame());
    GameData game = testObject1.updateGame(auth,newGame);

    assertEquals(newGame, game);
  }

  @Test
  void updateGameFails() throws DataAccessException {
    try {
      AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
      AuthData auth = new AuthData("qbarger", authToken.authToken());
      CreateGameData gamename = new CreateGameData("My Game");
      GameID gameID = testObject3.createGame(auth,gamename);
      GameData newGame = new GameData(gameID.gameID(),"Jeff","John","My Game",new ChessGame());
      GameData game = testObject1.updateGame(new AuthData("qbarger", authToken.authToken() + "b"), newGame);
      fail("Expected to say Authorization not found.");
    }
    catch (DataAccessException d) {
      assertEquals("Authorization not found.", d.getMessage());
    }
  }

  @Test
  void joinGame() throws DataAccessException {
    AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    AuthData auth = new AuthData("qbarger", authToken.authToken());
    CreateGameData gamename = new CreateGameData("My Game");
    GameID gameID = testObject3.createGame(auth, gamename);
    GameData newGame = new GameData(gameID.gameID(),"","qbarger","My Game",new ChessGame());
    JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
    GameData game = testObject1.joinGame(add,auth);

    assertEquals(newGame, game);
  }

  @Test
  void joinGameFails() throws DataAccessException {
    try {
      AuthData authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
      AuthData auth = new AuthData("qbarger", authToken.authToken());
      CreateGameData gamename = new CreateGameData("My Game");
      GameID gameID = testObject3.createGame(auth, gamename);
      GameData newGame = new GameData(gameID.gameID(),"","qbarger","My Game",new ChessGame());
      JoinGameData add = new JoinGameData("BLACK", gameID.gameID());
      GameData game = testObject1.joinGame(add, new AuthData("qbarger", authToken.authToken() + "b"));
      fail("Expected to throw a Data Access Exception.");
    }
    catch (DataAccessException d) {
      assertEquals("Authorization not found.", d.getMessage());
    }
  }
}