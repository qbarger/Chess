package service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  public ClearService testObject;
  public UserDao userTestDB;
  public AuthDao authTestDB;
  public GameDao gameTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    gameTestDB = new MemoryGameDao();
    testObject = new ClearService(userTestDB,authTestDB,gameTestDB);
  }

  @Test
  void clear() {
    testObject.userDB.createUser(new UserData("qbarger","jellyfish","jelly@gmail.com"));
    testObject.authDB.createAuth("qbarger");
    testObject.gameDB.createGame(new GameData(2,"john","james","game0",new ChessGame()));

    testObject.clear();

    assertEquals(true,testObject.userDB.isItEmpty());
    assertEquals(true,testObject.authDB.isItEmpty());
    assertEquals(true,testObject.gameDB.isItEmpty());
  }
}