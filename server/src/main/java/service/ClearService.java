package service;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;

public class ClearService {
  private UserDao userDB;
  private AuthDao authDB;
  private GameDao gameDB;

  public ClearService(UserDao userDB, AuthDao authDB, GameDao gameDB) {
    this.userDB = userDB;
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public void clear(){
    userDB.clear();
    authDB.clear();
    gameDB.clear();
  }
}
