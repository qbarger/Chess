package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;

public class LogoutService {
  private UserDao userDB;
  private AuthDao authDB;

  public LogoutService(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public void logout(AuthData auth) throws DataAccessException {
    if(userDB.checkUser(auth.username())){
      authDB.deleteAuth(auth);
    }
    else {
      throw new DataAccessException("User could not be found.");
    }
  }
}
