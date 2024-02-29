package service;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.CreateGameData;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
  public GameDao gameDB;
  public AuthDao authDB;
  public final CreateGameService createGameService;

  public CreateGameHandler(GameDao gameDB, AuthDao authDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
    this.createGameService = new CreateGameService(authDB, gameDB);
  }

  public Object createGame(Request req, Response res) throws DataAccessException {
    var create = new Gson().fromJson(req.body(), CreateGameData.class);
    String authToken = req.headers("authorization");
    if(authToken != null){
      var gameID = createGameService.createGame(authToken, create);
      res.status(200);
      return new Gson().toJson(gameID);
    }
    return "{}";
  }
}
