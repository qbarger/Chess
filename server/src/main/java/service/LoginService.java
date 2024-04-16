package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.ErrorData;
import model.UserData;

public class LoginService {
  public AuthDao authDB;
  public UserDao userDB;

  public LoginService(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public AuthData login(UserData user) throws DataAccessException {
    if(userDB.checkUser(user.username()) && userDB.checkPassword(user)){
      AuthData auth=authDB.createAuth(user.username());
      return auth;
    }
    else {
      ErrorData error = new ErrorData("Error: unauthorized");
      throw new DataAccessException(error.message(), 401);
    }
  }
}
