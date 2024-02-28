package service;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.GameData;
import model.GameID;

public class CreateGameService {
  private GameDao gameDB;
  private AuthDao authDB;

  public CreateGameService(AuthDao authDB,GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameID createGame(AuthData auth, String gamename) throws DataAccessException {
    AuthData checkAuth = authDB.getAuth(auth.username());
    int gameID = gameDB.listSize() + 1;
    if(checkAuth.authToken() == auth.authToken()){
      GameData game = new GameData(gameID,"","",gamename,new ChessGame());
      gameDB.createGame(game);
    }
    else {
      throw new DataAccessException("Verification not found.");
    }
    GameID id = new GameID(gameID);
    return id;
  }
}
