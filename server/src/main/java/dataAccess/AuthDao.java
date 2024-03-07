package dataAccess;

import model.AuthData;

public interface AuthDao {
  public AuthData createAuth(String username) throws DataAccessException;

  public AuthData getAuth(String authToken);

  public void deleteAuth(String authToken);

  public boolean checkAuth(String authToken);

  public void clear() throws DataAccessException;

  public boolean isItEmpty();
}
