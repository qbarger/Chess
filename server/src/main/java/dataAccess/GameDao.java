package dataAccess;

import model.GameData;

public interface GameDao {

  public void createGame(GameData game);

  public void getGame();

  public void updateGame();

  public void listGames();

  public void clear();

}
