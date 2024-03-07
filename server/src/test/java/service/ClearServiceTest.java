package service;

import chess.ChessGame;
import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryGameDao;
import dataAccess.memoryDAOs.MemoryUserDao;
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
  void clear() throws DataAccessException{
    //clearService.userDB.createUser(new UserData("qbarger","jellyfish","jelly@gmail.com"));
    //clearService.authDB.createAuth("qbarger");
    clearService.gameDB.createGame(new GameData(2,"john","james","game0",new ChessGame()));

    clearService.clear();

    assertEquals(true,clearService.authDB.isItEmpty());
    assertEquals(true,clearService.authDB.isItEmpty());
    assertEquals(true,clearService.gameDB.isItEmpty());
  }
}