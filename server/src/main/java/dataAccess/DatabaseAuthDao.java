package dataAccess;

import model.AuthData;

import java.sql.Statement;

public class DatabaseAuthDao implements AuthDao{
  @Override
  public AuthData createAuth(String username) {
    return null;
  }

  @Override
  public AuthData getAuth(String authToken) {
    return null;
  }

  @Override
  public void deleteAuth(String authToken) {

  }

  @Override
  public boolean checkAuth(String authToken) {
    return false;
  }

  @Override
  public void clear() {
    var statement = "Truncate Auth";
  }

  @Override
  public boolean isItEmpty() {
    return false;
  }


}
