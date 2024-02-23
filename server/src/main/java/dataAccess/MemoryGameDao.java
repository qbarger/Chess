package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDao implements GameDao{
  private Map<String, GameData> gameInfo = new HashMap<>();
  @Override
  public void createGame(GameData game){
    gameInfo.put(game.gameName(), game);
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
