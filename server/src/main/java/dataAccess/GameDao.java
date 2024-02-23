package dataAccess;

public interface GameDao {

  public void createGame(String gameName);

  public void getGame();

  public void updateGame();

  public void listGames();

  public void clear();

}
