package dataAccess;

import model.AuthData;

public interface AuthDao {
  public void createAuth(AuthData auth);

  public void getAuth(String authToken);

  public void deleteAuth();

  public void clear();

  public boolean isItEmpty();
}
