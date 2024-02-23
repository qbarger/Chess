package dataAccess;

public interface AuthDao {
  public void createAuth();

  public void getAuth(String authToken);

  public void deleteAuth();

  public void clear();

}
