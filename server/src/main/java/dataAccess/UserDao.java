package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public interface UserDao {

  public void createUser(UserData user);

  public boolean checkUser(String username);

  public boolean checkPassword(UserData user);

  public void clear();
}
