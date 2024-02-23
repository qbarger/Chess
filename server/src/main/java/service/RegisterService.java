package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.UserData;

public class RegisterService {
  private AuthDao authDB;
  private UserDao userDB;

  public String register(UserData user) throws DataAccessException{
    if(userDB.checkUser(user.username())) {
      throw new DataAccessException("Username already exists.");
    }
    else {
      userDB.createUser(user);
      authDB.createAuth(user.username());
    }
    AuthData auth = authDB.getAuth(user.username());
    return auth.authToken();
  }

}
