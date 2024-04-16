package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.GameList;

import java.sql.SQLException;

public interface GameDao {

  public int createGame(GameData game) throws DataAccessException;

  public GameData getGame(int gameID) throws DataAccessException, SQLException;

  public void joinGame(GameData game) throws DataAccessException;

  public GameList listGames() throws DataAccessException;

  public void leaveGame(int gameID, String username) throws DataAccessException, SQLException;

  public GameData makeMove(int gameID, ChessGame game) throws DataAccessException, SQLException;

  public int getMaxID() throws DataAccessException;

  public void clear() throws DataAccessException;

  public boolean isItEmpty();
}
