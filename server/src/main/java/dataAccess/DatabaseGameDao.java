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
  @Override
  public void createGame(GameData game) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Insert into Game (gameID, whiteUsername, blackUsername, gameName, game, json) Values (?,?,?,?,?,?)";
    var json = new Gson().toJson(game);
    var gameString = new Gson().toJson(new ChessGame());
    executeCommand(statement, game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), gameString, json);
  }

  @Override
  public GameData getGame(int gameID) {
    return null;
  }

  @Override
  public void joinGame(GameData game) {

  }

  @Override
  public GameList listGames() {
    return null;
  }

  @Override
  public int listSize() throws DataAccessException{
    int size = 0;
    try (var conn = DatabaseManager.getConnection()) {
      var statement = "SELECT gameID, FROM Game";
      try (var ps = conn.prepareStatement(statement)) {
        try (var rs = ps.executeQuery()) {
          while (rs.next()) {
            size++;
          }
        }
      }
      return size;
    } catch (Exception e) {
      throw new DataAccessException("Unable to read data: %s", 500);
    }
  }

  @Override
  public void clear() throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Truncate Game";
    executeCommand(statement);
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
          } else if (param instanceof UserData u) {
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
