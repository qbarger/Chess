package dataAccess;

import model.GameData;
import model.GameList;

public class DatabaseGameDao implements GameDao{
  @Override
  public void createGame(GameData game) {

  }

  @Override
  public GameData getGame(int gameID) {
    return null;
  }

  @Override
  public void joinGame(GameData game) {

  }

  @Override
  public GameList listGames() {
    return null;
  }

  @Override
  public int listSize() {
    return 0;
  }

  @Override
  public void clear() {
    var statement = "Truncate Game";
  }

  @Override
  public boolean isItEmpty() {
    return false;
  }
}
