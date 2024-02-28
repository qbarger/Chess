package service;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.UserData;
import spark.Request;
import spark.Response;

public class LoginHandler {
  public AuthDao authDB;
  public UserDao userDB;
  public final LoginService loginService;

  public LoginHandler(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
    this.loginService = new LoginService(userDB, authDB);
  }

  public Object login(Request req, Response res) throws DataAccessException {
    var log = new Gson().fromJson(req.body(), UserData.class);
    var token = loginService.login(log);
    if(token != null){
      res.status(200);
    }
    else {
      res.status(401);
    }
    return new Gson().toJson(token);
  }
}
