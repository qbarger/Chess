package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.UserData;

public class LogoutService {
  public UserDao userDB;
  public AuthDao authDB;

  public LogoutService(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public void logout(String authToken) throws DataAccessException {
    if(authDB.checkAuth(authToken)){
      authDB.deleteAuth(authToken);
    }
    else {
      throw new DataAccessException("Error: unauthorized", 401);
    }
  }
}
