package dataAccess;

import model.GameData;

public interface GameDao {

  public void createGame(GameData game);

  public GameData getGame(int gameID) throws DataAccessException;

  public void updateGame();

  public void listGames();

  public int listSize();

  public void clear();

  public boolean isItEmpty();
}
