package dataAccess;

import model.AuthData;

public interface AuthDao {
  public AuthData createAuth(String username) throws DataAccessException;

  public AuthData getAuth(String authToken);

  public void deleteAuth(String authToken) throws DataAccessException;

  public boolean checkAuth(String authToken) throws DataAccessException;

  public void clear() throws DataAccessException;

  public boolean isItEmpty();
}
