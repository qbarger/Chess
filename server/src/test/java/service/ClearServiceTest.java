package service;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClearServiceTest {
  public ClearService clearService;
  public UserDao userTestDB;
  public AuthDao authTestDB;
  public GameDao gameTestDB;

  @BeforeEach
  void setup() throws DataAccessException {
    userTestDB = new DatabaseUserDao();
    authTestDB = new DatabaseAuthDao();
    gameTestDB = new DatabaseGameDao();
    clearService = new ClearService(userTestDB,authTestDB,gameTestDB);
  }

  @Test
  void clear() throws DataAccessException{
    //clearService.userDB.createUser(new UserData("qbarger","jellyfish","jelly@gmail.com"));
    //clearService.authDB.createAuth("qbarger");
    clearService.gameDB.createGame(new GameData(2,"john","james","game0",new ChessGame()));

    clearService.clear();

    assertTrue(clearService.authDB.isItEmpty());
    assertTrue(clearService.authDB.isItEmpty());
    assertTrue(clearService.gameDB.isItEmpty());
  }
}