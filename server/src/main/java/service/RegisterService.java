package service;

import dataAccess.AuthDao;
import dataAccess.UserDao;

public class RegisterService {
  private AuthDao authDB;
  private UserDao userDB;

  public void register(String username, String password, String email){
    userDB.getUser();
  }

}
