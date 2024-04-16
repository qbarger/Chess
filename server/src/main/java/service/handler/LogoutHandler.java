package service.handler;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
  public UserDao userDB;
  public AuthDao authDB;
  public final LogoutService logoutService;

  public LogoutHandler(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
    this.logoutService = new LogoutService(userDB, authDB);
  }

  public Object logout(Request req, Response res) throws DataAccessException {
    String authToken = req.headers("authorization");
    if(authToken != null){
      logoutService.logout(authToken);
      res.status(200);
    }

    return "{}";
  }
}
