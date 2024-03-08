package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameList;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class DatabaseGameDao implements GameDao{

  private int listSize = 0;
  @Override
  public void createGame(GameData game) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Insert into Game (gameID, whiteUsername, blackUsername, gameName, game, json) Values (?,?,?,?,?,?)";
    var json = new Gson().toJson(game);
    var gameString = new Gson().toJson(game.game());
    executeCommand(statement, game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), gameString, json);
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    try (var conn = DatabaseManager.getConnection()){
      var statement = "Select gameID, json From Game Where gameID = ?";
      try (var ps = conn.prepareStatement(statement)){
        ps.setInt(1, gameID);
        try (var rs = ps.executeQuery()){
          if(rs.next()){
            var json = rs.getString("json");
            var game = new Gson().fromJson(json, GameData.class);
            return game;
          }
          else {
            throw new DataAccessException("Game not found.", 500);
          }
        }
      }
    } catch (DataAccessException exception) {
      throw new DataAccessException(String.format("Unable to read data: %s", exception.getMessage()), 500);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void joinGame(GameData game) {

  }

  @Override
  public GameList listGames() {
    return null;
  }

  @Override
  public void clear() throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Truncate Game";
    executeCommand(statement);
  }

  @Override
  public int getListSize(){
    this.listSize++;
    return listSize;
  }

  @Override
  public boolean isItEmpty() {
    return false;
  }

  private void executeCommand(String statement, Object...params) throws DataAccessException{
    try (var conn = DatabaseManager.getConnection()){
      try (var ps = conn.prepareStatement(statement)){
        for(var i = 0; i < params.length; i++){
          var param = params[i];
          if(param instanceof String u){
            ps.setString(i + 1, u);
          } else if (param instanceof Integer u) {
            ps.setInt(i + 1, u);
          } else if (param instanceof GameData u) {
            ps.setString(i + 1, u.toString());
          } else if (param == null) {
            ps.setNull(i + 1, NULL);
          }
        }
        ps.executeUpdate();
      }
    }
    catch (SQLException exception) {
      throw new DataAccessException(String.format("unable to update database: %s, %s", statement, exception.getMessage()), 500);
    }
  }
}
