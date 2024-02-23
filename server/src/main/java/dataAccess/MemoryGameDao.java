package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDao {
  private Map<String, GameData> gameInfo = new HashMap<>();
  @Override
  public void createGame(String gameName){

  }

  @Override
  public void getGame(){

  }

  @Override
  public void updateGame(){

  }

  @Override
  public void listGames(){

  }

  @Override
  public void clear(){
    gameInfo.clear();
  }
}
