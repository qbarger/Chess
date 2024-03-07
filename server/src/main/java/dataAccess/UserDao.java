package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public interface UserDao {

  public void createUser(UserData user) throws DataAccessException;

  public boolean checkUser(String username) throws DataAccessException;

  public boolean checkPassword(UserData user) throws DataAccessException;

  public void clear() throws DataAccessException;
}
