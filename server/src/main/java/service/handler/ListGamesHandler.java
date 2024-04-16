package service.handler;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.GameList;
import service.ListGamesService;
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
      GameList list = listGamesService.listGames(authToken);
      return new Gson().toJson(list);
    }
    return "{}";
  }
}
