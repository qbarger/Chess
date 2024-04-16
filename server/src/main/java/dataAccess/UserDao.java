package dataAccess;

import model.UserData;

public interface UserDao {

  public void createUser(UserData user) throws DataAccessException;

  public boolean checkUser(String username) throws DataAccessException;

  public boolean checkPassword(UserData user) throws DataAccessException;

  public void clear() throws DataAccessException;
}
