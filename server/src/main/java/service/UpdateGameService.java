package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.GameData;

public class UpdateGameService {
  private AuthDao authDB;
  private GameDao gameDB;

  public UpdateGameService(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameData updateGame(AuthData auth, GameData game) throws DataAccessException {
    AuthData checkAuth = authDB.getAuth(auth.username());
    if(checkAuth.authToken() == auth.authToken()){
      gameDB.updateGame(game);
      return game;
    }
    else {
      throw new DataAccessException("Authorization not found.");
    }
  }
}
