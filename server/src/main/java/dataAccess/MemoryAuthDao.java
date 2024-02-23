package dataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao{
  private Map<String, AuthData> authInfo = new HashMap<>();

  @Override
  public void createAuth(AuthData auth){
    String authToken =UUID.randomUUID().toString();
    authInfo.put(authToken,auth);
  }

  @Override
  public void getAuth(String authToken){

  }

  @Override
  public void deleteAuth(){

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
