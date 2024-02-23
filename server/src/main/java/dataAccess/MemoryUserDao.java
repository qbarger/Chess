package dataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDao implements UserDao{
  private Map<String, UserData> userInfo = new HashMap<>();

  @Override
  public void createUser(UserData user){
    userInfo.put(user.username(),user);
  }

  @Override
  public UserData getUser(String username){
    return userInfo.get(username);
  }

  @Override
  public void clear(){
    userInfo.clear();
  }
}
