package dataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDao implements AuthDao{
  private Map<String, AuthData> authInfo = new HashMap<>();

  @Override
  public void createAuth(){

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
}
