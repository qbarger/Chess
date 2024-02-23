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

  public boolean checkUser(String username){
    if(userInfo.containsKey(username)){
      return true;
    }
    return false;
  }

  @Override
  public void clear(){
    userInfo.clear();
  }

  @Override
  public boolean isItEmpty(){
    if(userInfo.size() == 0){
      return true;
    }
    return false;
  }
}
