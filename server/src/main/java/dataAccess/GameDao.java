package dataAccess;

import model.GameData;
import model.GameList;
import model.JoinGameData;

import java.util.ArrayList;

public interface GameDao {

  public void createGame(GameData game);

  public GameData getGame(int gameID);

  public void joinGame(GameData game);

  public GameList listGames();

  public int listSize();

  public void clear() throws DataAccessException;

  public boolean isItEmpty();
}
