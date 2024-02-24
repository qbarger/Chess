package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDao implements GameDao{
  private Map<String, GameData> gameInfo = new HashMap<>();
  @Override
  public void createGame(GameData game){
    gameInfo.put(game.gameName(), game);
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException{
    for(GameData game : gameInfo.values()){
      if(game.gameID() == gameID){
        return game;
      }
    }
    throw new DataAccessException("Game does not exist.");
  }

  @Override
  public void updateGame(){

  }

  @Override
  public ArrayList<GameData> listGames(){
    ArrayList<GameData> gameList = new ArrayList<>();
    for(GameData game : gameInfo.values()){
      gameList.add(game);
    }
    return gameList;
  }

  @Override
  public int listSize(){
    return gameInfo.size();
  }

  @Override
  public void clear(){
    gameInfo.clear();
  }

  @Override
  public boolean isItEmpty(){
    if(gameInfo.size() == 0){
      return true;
    }
    return false;
  }
}
