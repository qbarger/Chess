package service;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
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

  public GameID createGame(String authToken, CreateGameData gamename) throws DataAccessException {
    int gameID;
    if(authDB.checkAuth(authToken)){
      GameData game = new GameData(0,null,null,gamename.gameName(),new ChessGame());

      gameID = gameDB.createGame(game);
    }
    else {
      throw new DataAccessException("Error: unauthorized", 401);
    }
    GameID id = new GameID(gameID);
    return id;
  }
}
