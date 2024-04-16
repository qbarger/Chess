package dataAccess.memoryDAOs;

import dataAccess.AuthDao;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {
  private Map<String, AuthData> authInfo = new HashMap<>();

  @Override
  public AuthData createAuth(String username){
    String authToken =UUID.randomUUID().toString();
    AuthData auth = new AuthData(username, authToken);
    authInfo.put(authToken,auth);
    return auth;
  }

  @Override
  public AuthData getAuth(String authToken){
    return authInfo.get(authToken);
  }

  @Override
  public void deleteAuth(String authToken){
    authInfo.remove(authToken);
  }

  @Override
  public boolean checkAuth(String authToken){
    if(authInfo.containsKey(authToken)){
      return true;
    }
    else {
      return false;
    }
  }


  @Override
  public void clear(){
    authInfo.clear();
  }

  @Override
  public boolean isItEmpty(){
    if(authInfo.size() == 0){
      return true;
    }
    return false;
  }
}
