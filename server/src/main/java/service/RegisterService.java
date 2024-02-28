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
      throw new DataAccessException("Username already exists.");
    }
    else {
      userDB.createUser(user);
      AuthData auth = authDB.createAuth(user.username());
      return auth;
    }
  }

}
