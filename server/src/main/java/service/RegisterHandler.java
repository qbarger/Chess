package service;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.UserData;
import spark.Request;
import spark.Response;

public class RegisterHandler {
  public AuthDao authDB;
  public UserDao userDB;
  public final RegisterService registerService;

  public RegisterHandler(UserDao userDB, AuthDao authDB){
    this.userDB = userDB;
    this.authDB = authDB;
    this.registerService = new RegisterService(userDB, authDB);
  }

  public Object register(Request req, Response res) throws DataAccessException {
    var reg = new Gson().fromJson(req.body(), UserData.class);
    var token = registerService.register(reg);
    if(token != null) {
      res.status(200);
    }
    else {
      res.status(400);
    }
    return new Gson().toJson(token);
  }
}
