package service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
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
    String authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
    AuthData auth = new AuthData(authToken,"qbarger");
    int gameID = testObject3.createGame(auth,"My Game");
    GameData newGame = new GameData(gameID,"Jeff","John","My Game",new ChessGame());
    GameData game = testObject1.updateGame(auth,newGame);

    assertEquals(newGame, game);
  }

  @Test
  void updateGameFails() throws DataAccessException {
    try {
      String authToken = testObject2.register(new UserData("qbarger","johnnyland1","kingkong@gmail.com"));
      AuthData auth = new AuthData(authToken,"qbarger");
      int gameID = testObject3.createGame(auth,"My Game");
      GameData newGame = new GameData(gameID,"Jeff","John","My Game",new ChessGame());
      GameData game = testObject1.updateGame(new AuthData(authToken + "B","qbarger"),newGame);
      fail("Expected to say Authorization not found.");
    }
    catch (DataAccessException d) {
      assertEquals("Authorization not found.", d.getMessage());
    }
  }
}