package service;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.ErrorData;
import model.GameData;
import model.JoinGameData;

import java.util.Objects;

public class UpdateGameService {
  public AuthDao authDB;
  public GameDao gameDB;

  public UpdateGameService(AuthDao authDB, GameDao gameDB){
    this.authDB = authDB;
    this.gameDB = gameDB;
  }

  public void joinGame(JoinGameData info, String authToken) throws DataAccessException{
    if(authDB.checkAuth(authToken)) {
      if(gameDB.getGame(info.gameID()) != null) {
        GameData game=gameDB.getGame(info.gameID());
        if (info.playerColor() != null) {
          if (info.playerColor().equals("WHITE")) {
            if (game.whiteUsername() == null) {
              AuthData auth=authDB.getAuth(authToken);
              GameData newGame=new GameData(info.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
              gameDB.joinGame(newGame);
            } else {
              ErrorData error=new ErrorData("Error: already taken");
              throw new DataAccessException(error.message(), 403);
            }
          } else if (info.playerColor().equals("BLACK")) {
            if (game.blackUsername() == null) {
              AuthData auth=authDB.getAuth(authToken);
              GameData newGame=new GameData(info.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
              gameDB.joinGame(newGame);
            } else {
              ErrorData error=new ErrorData("Error: already taken");
              throw new DataAccessException(error.message(), 403);
            }
          } else {
            GameData newGame=new GameData(info.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
            gameDB.joinGame(newGame);
          }
        } else {
          GameData newGame=new GameData(info.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
          gameDB.joinGame(newGame);
        }
      }
      else {
        ErrorData error = new ErrorData("Error: bad request");
        throw new DataAccessException(error.message(), 400);
      }
    }
    else {
      ErrorData error = new ErrorData("Error: unauthorized");
      throw new DataAccessException(error.message(), 401);
    }
  }
}
