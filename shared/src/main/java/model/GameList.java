package model;

import java.util.ArrayList;

public record GameList(ArrayList<GameData> games) {

  public GameData getGame(int id) throws Exception {
    if(id < 0 || id >= games.size()){
      throw new Exception("ID not valid");
    }
    return games.stream().skip(id).findFirst().orElse(null);
  }

}
