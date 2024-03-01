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
  public ClearService clearService;
  public UserDao userTestDB;
  public AuthDao authTestDB;
  public GameDao gameTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    gameTestDB = new MemoryGameDao();
    clearService = new ClearService(userTestDB,authTestDB,gameTestDB);
  }

  @Test
  void clear() {
    clearService.userDB.createUser(new UserData("qbarger","jellyfish","jelly@gmail.com"));
    clearService.authDB.createAuth("qbarger");
    clearService.gameDB.createGame(new GameData(2,"john","james","game0",new ChessGame()));

    clearService.clear();

    assertEquals(true,clearService.userDB.isItEmpty());
    assertEquals(true,clearService.authDB.isItEmpty());
    assertEquals(true,clearService.gameDB.isItEmpty());
  }
}