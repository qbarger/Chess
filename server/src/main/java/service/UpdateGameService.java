package service;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.GameData;
import model.JoinGameData;

public class UpdateGameService {
  public AuthDao authDB;
  public GameDao gameDB;

  public UpdateGameService(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public GameData updateGame(String authToken, GameData game) throws DataAccessException {
    if(authDB.checkAuth(authToken)){
      gameDB.updateGame(game);
      return game;
    }
    else {
      throw new DataAccessException("Authorization not found.");
    }
  }

  public GameData joinGame(JoinGameData info, String authToken) throws DataAccessException{
    AuthData auth = authDB.getAuth(authToken);
    if(authDB.checkAuth(authToken)){
      if(info.playerColor() == "WHITE"){
        GameData game = gameDB.getGame(info.gameID());
        GameData newGame = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), new ChessGame());
        gameDB.updateGame(newGame);
        return newGame;
      }
      else {
        GameData game = gameDB.getGame(info.gameID());
        GameData newGame = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), new ChessGame());
        gameDB.updateGame(newGame);
        return newGame;
      }
    }
    else {
      throw new DataAccessException("Authorization not found.");
    }
  }
}