package dataAccess;

import model.AuthData;

public interface AuthDao {
  public void createAuth(String username);

  public AuthData getAuth(String username);

  public void deleteAuth(AuthData auth);

  public boolean checkAuth(String authToken);

  public void clear();

  public boolean isItEmpty();
}
