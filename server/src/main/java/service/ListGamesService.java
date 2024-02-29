package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import dataAccess.UserDao;
import model.AuthData;
import model.GameData;
import model.GameList;

import java.util.ArrayList;

public class ListGamesService {
  public AuthDao authDB;
  public GameDao gameDB;

  public ListGamesService(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameList listGames(String authToken) throws DataAccessException {
    if(authDB.checkAuth(authToken)){
      GameList gameList = new GameList(gameDB.listGames().gameList());
      return gameList;
    }
    else {
      throw  new DataAccessException("Error: unauthorized", 401);
    }
  }
}
