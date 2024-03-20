package dataAccess.memoryDAOs;

import dataAccess.GameDao;
import model.GameData;
import model.GameList;
import model.JoinGameData;

import java.util.*;

public class MemoryGameDao implements GameDao {
  private Map<Integer, GameData> gameInfo = new HashMap<>();
  private int listSize = 0;
  @Override
  public int createGame(GameData game){
    gameInfo.put(game.gameID(), game);
    return game.gameID();
  }

  @Override
  public GameData getGame(int gameID) {
    return gameInfo.get(gameID);
  }

  @Override
  public void joinGame(GameData game){
    gameInfo.put(game.gameID(), game);
  }

  @Override
  public GameList listGames(){
    ArrayList<GameData> list = new ArrayList<>();
    list.addAll(gameInfo.values());
    GameList gameList = new GameList(list);
    return gameList;
  }

  @Override
  public int getMaxID(){
    this.listSize++;
    return listSize;
  }

  @Override
  public void clear(){
    gameInfo.clear();
  }

  @Override
  public boolean isItEmpty(){
    if(gameInfo.isEmpty()){
      return true;
    }
    return false;
  }
}
