package service;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
  public AuthDao authDB;
  public GameDao gameDB;
  public final ListGamesService listGamesService;

  public ListGamesHandler(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
    this.listGamesService = new ListGamesService(authDB, gameDB);
  }

  public Object listGames(Request req, Response res) throws DataAccessException {
    String authToken = req.headers("authorization");
    if(authToken != null){
      res.status(200);
      var list = listGamesService.listGames(authToken);
      res.status(200);
      return new Gson().toJson(list);
    }
    else {
      res.status(401);
      return "{}";
    }
  }
}
