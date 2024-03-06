package dataAccess.memoryDAOs;
import dataAccess.UserDao;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryUserDao implements UserDao {
  private Map<String, UserData> userInfo = new HashMap<>();

  @Override
  public void createUser(UserData user){
    userInfo.put(user.username(),user);
  }

  @Override
  public boolean checkUser(String username){
    if(userInfo.containsKey(username)){
      return true;
    }
    return false;
  }

  @Override
  public boolean checkPassword(UserData user){
    UserData check = userInfo.get(user.username());
    if(Objects.equals(check.password(), user.password())){
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public void clear(){
    userInfo.clear();
  }

}
