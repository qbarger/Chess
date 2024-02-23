package dataAccess;

import model.AuthData;

public interface AuthDao {
  public void createAuth(String username);

  public AuthData getAuth(String username);

  public void deleteAuth();

  public void clear();

  public boolean isItEmpty();
}
