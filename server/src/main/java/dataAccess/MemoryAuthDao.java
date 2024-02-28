package dataAccess;
import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao{
  private Map<String, AuthData> authInfo = new HashMap<>();

  @Override
  public void createAuth(String username){
    String authToken =UUID.randomUUID().toString();
    AuthData auth = new AuthData(username,authToken);
    authInfo.put(username,auth);
  }

  @Override
  public AuthData getAuth(String username){
    return authInfo.get(username);
  }

  @Override
  public void deleteAuth(AuthData auth){
    AuthData tempAuth = new AuthData(auth.username(), null);
    authInfo.put(auth.username(), tempAuth);
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
