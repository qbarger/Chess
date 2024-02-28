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
  private AuthDao authDB;
  private GameDao gameDB;

  public ListGamesService(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameList listGames(AuthData auth) throws DataAccessException {
    AuthData checkAuth = authDB.getAuth(auth.username());
    if(checkAuth.authToken() == auth.authToken()){
      GameList gameList = new GameList(gameDB.listGames().gameList());
      return gameList;
    }
    else {
      throw  new DataAccessException("Authorization not found.");
    }
  }
}
