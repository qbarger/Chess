package dataAccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDao {

  public void createGame(GameData game);

  public GameData getGame(int gameID) throws DataAccessException;

  public void updateGame();

  public ArrayList<GameData> listGames();

  public int listSize();

  public void clear();

  public boolean isItEmpty();
}
