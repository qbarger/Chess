package service.handler;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
  public UserDao userDB;
  public AuthDao authDB;
  public GameDao gameDB;
  public final ClearService clearHandler;

  public ClearHandler(UserDao userDB, AuthDao authDB, GameDao gameDB){
    this.userDB = userDB;
    this.authDB = authDB;
    this.gameDB = gameDB;
    this.clearHandler = new ClearService(userDB, authDB, gameDB);
  }

  public Object clear(Request req, Response res){
    clearHandler.clear();
    res.status(200);
    return "{}";
  }

}
