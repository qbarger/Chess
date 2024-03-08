package dataAccess;

import model.GameData;
import model.GameList;
import model.JoinGameData;

import java.util.ArrayList;

public interface GameDao {

  public void createGame(GameData game) throws DataAccessException;

  public GameData getGame(int gameID) throws DataAccessException;

  public void joinGame(GameData game) throws DataAccessException;

  public GameList listGames() throws DataAccessException;

  public int getListSize();

  public void clear() throws DataAccessException;

  public boolean isItEmpty();
}
