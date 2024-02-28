package dataAccess;

import model.GameData;
import model.GameList;

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
  public GameData updateGame(GameData game){
    for(String key : gameInfo.keySet()){
      if(key == game.gameName()){
        gameInfo.put(key,game);
      }
    }
    return gameInfo.get(game.gameName());
  }

  @Override
  public GameList listGames(){
    ArrayList<GameData> list = new ArrayList<>();
    for(GameData game : gameInfo.values()){
      list.add(game);
    }
    GameList gameList = new GameList(list);
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
