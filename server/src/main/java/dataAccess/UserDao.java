package dataAccess;

public interface UserDao {

  public void createUser(String username, String password);

  public void getUser(String username);

  public void clear();

}
