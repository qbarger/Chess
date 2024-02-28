package dataAccess;

import model.AuthData;

public interface AuthDao {
  public AuthData createAuth(String username);

  public AuthData getAuth(String authToken);

  public void deleteAuth(String authToken);

  public boolean checkAuth(String authToken);

  public void clear();

  public boolean isItEmpty();
}
