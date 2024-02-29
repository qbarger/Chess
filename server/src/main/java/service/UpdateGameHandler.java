package service;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.GameData;
import model.JoinGameData;
import spark.Request;
import spark.Response;

public class UpdateGameHandler {
  public AuthDao authDB;
  public GameDao gameDB;
  public final UpdateGameService updateGameService;

  public UpdateGameHandler(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
    this.updateGameService = new UpdateGameService(authDB, gameDB);
  }

  public Object joinGame(Request req, Response res) throws DataAccessException{
    var data = new Gson().fromJson(req.body(), JoinGameData.class);
    String authToken =req.headers("authorization");
    if(authToken != null){
      res.status(200);
      updateGameService.joinGame(data, authToken);
      return "{}";
    }
    else {
      return "{}";
    }
  }
}
