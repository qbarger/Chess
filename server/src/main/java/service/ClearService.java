package service;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;

public class ClearService {
  public UserDao userDB;
  public AuthDao authDB;
  public GameDao gameDB;

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
