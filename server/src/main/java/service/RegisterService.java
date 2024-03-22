package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.AuthData;
import model.UserData;

public class RegisterService {
  public AuthDao authDB;
  public UserDao userDB;

  public RegisterService(UserDao userDB,AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
  }

  public AuthData register(UserData user) throws DataAccessException{
    if(userDB.checkUser(user.username())) {
      throw new DataAccessException("Error: already taken", 403);
    }
    else if(user.username() == null || user.username().equals("") || user.password() == null || user.password().equals("") || user.email() == null || user.email().equals("")) {
      throw new DataAccessException("Error: bad request", 400);
    }
    else {
      userDB.createUser(user);
      AuthData auth = authDB.createAuth(user.username());
      return auth;
    }
  }

}
