package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class LoginService {
  private AuthDao authDB;
  private UserDao userDB;

  public LoginService(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public String login(UserData user) throws DataAccessException {
    if(userDB.checkUser(user.username())){
      authDB.createAuth(user.username());
      AuthData auth = authDB.getAuth(user.username());
      return auth.authToken();
    }
    else {
      throw new DataAccessException("Username does not exist.");
    }
  }
}
