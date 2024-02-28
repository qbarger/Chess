package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class LoginService {
  public AuthDao authDB;
  public UserDao userDB;

  public LoginService(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public AuthData login(UserData user) throws DataAccessException {
    if(userDB.checkUser(user.username())){
      AuthData auth = authDB.createAuth(user.username());
      return auth;
    }
    else {
      throw new DataAccessException("Username does not exist.");
    }
  }
}
