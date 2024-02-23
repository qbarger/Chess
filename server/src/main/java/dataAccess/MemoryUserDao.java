package dataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDao implements UserDao{
  private Map<String, UserData> userInfo = new HashMap<>();

  @Override
  public void createUser(String username, String password){

  }

  @Override
  public void getUser(String username){

  }

  @Override
  public void clear(){
    userInfo.clear();
  }
}
