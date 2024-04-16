package service;

import chess.ChessGame;
import chess.ChessPiece;
import chess.InvalidMoveException;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.*;

import java.sql.SQLException;

public class UpdateGameService {
  public AuthDao authDB;
  public GameDao gameDB;

  public UpdateGameService(AuthDao authDB, GameDao gameDB) {
    this.authDB=authDB;
    this.gameDB=gameDB;
  }

  public void joinGame(JoinGameData info, String authToken) throws DataAccessException, SQLException {
    if (authDB.checkAuth(authToken)) {
      if (gameDB.getGame(info.gameID()) != null) {
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
      } else {
        ErrorData error=new ErrorData("Error: bad request");
        throw new DataAccessException(error.message(), 400);
      }
    } else {
      ErrorData error=new ErrorData("Error: unauthorized");
      throw new DataAccessException(error.message(), 401);
    }
  }

  public void joinGameObserver(int gameID, String authtoken) throws DataAccessException, SQLException {
    if (authDB.checkAuth(authtoken)) {
      GameData game=gameDB.getGame(gameID);
    }
  }

  public GameData makeMove(MakeMoveData moveData, String authToken) throws DataAccessException, InvalidMoveException {
    if(authDB.checkAuth(authToken)){
      try {
        AuthData authData=authDB.getAuth(authToken);
        GameData gameData=gameDB.getGame(moveData.gameID());
        ChessGame chessGame=gameData.game();
        ChessPiece piece=gameData.game().getPiece(moveData.move());
        if (authData.username().equals(gameData.whiteUsername())) {
          if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            boolean check;
            check=chessGame.makeMove(moveData.move());
            if (check) {
              return gameDB.makeMove(moveData.gameID(), chessGame);
            } else {
              throw new DataAccessException("Error: invalid move...", 400);
            }
          } else {
            throw new DataAccessException("Error: invalid move...", 400);
          }
        } else if (authData.username().equals(gameData.blackUsername())) {
          if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            boolean check;
            check=chessGame.makeMove(moveData.move());
            if (check) {
              return gameDB.makeMove(moveData.gameID(), chessGame);
            } else {
              throw new DataAccessException("Error: invalid move...", 400);
            }
          } else {
            throw new DataAccessException("Error: invalid move...", 400);
          }
        } else {
          throw new DataAccessException("Error: cannot make move as observer...", 400);
        }
      } catch (DataAccessException | SQLException exception){
        throw new DataAccessException("Error: invalid move...", 400);
      }
    } else {
      throw new DataAccessException("Error: unauthorized...", 401);
    }
  }

  public void leaveGame(int gameID, String authToken) throws DataAccessException, SQLException {
    if(authDB.checkAuth(authToken)){
      AuthData auth = authDB.getAuth(authToken);
      gameDB.leaveGame(gameID, auth.username());
    } else {
      ErrorData error=new ErrorData("Error: unauthorized");
      throw new DataAccessException(error.message(), 401);
    }
  }
}
