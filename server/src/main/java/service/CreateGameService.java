package service;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.CreateGameData;
import model.GameData;
import model.GameID;

public class CreateGameService {
  public GameDao gameDB;
  public AuthDao authDB;

  public CreateGameService(AuthDao authDB,GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameID createGame(AuthData auth, CreateGameData gamename) throws DataAccessException {
    AuthData checkAuth = authDB.getAuth(auth.username());
    int gameID = gameDB.listSize() + 1;
    if(checkAuth.authToken() == auth.authToken()){
      GameData game = new GameData(gameID,"","",gamename.gameName(),new ChessGame());
      gameDB.createGame(game);
    }
    else {
      throw new DataAccessException("Verification not found.");
    }
    GameID id = new GameID(gameID);
    return id;
  }
}
