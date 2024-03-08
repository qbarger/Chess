package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.ErrorData;
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
    DatabaseManager databaseManager=new DatabaseManager();
    databaseManager.configureDatabase();
    var statement="Insert into Game (gameID, whiteUsername, blackUsername, gameName, game, json) Values (?,?,?,?,?,?)";
    var json=new Gson().toJson(game);
    var gameString=new Gson().toJson(game.game());
    executeCommand(statement, game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), gameString, json);
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
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
            ErrorData error = new ErrorData("Game not found.");
            throw new DataAccessException("Game not found.", 400);
          }
        }
      }
    } catch (DataAccessException exception) {
      ErrorData error = new ErrorData("Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 400);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void joinGame(GameData game) throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      String statement = "UPDATE Game SET gameID = ?, whiteUsername = ?, blackUsername = ?, gameName = ?, game = ?, json = ? WHERE gameID = ?";
      var whiteUsername = new Gson().toJson(game.whiteUsername());
      var blackUsername = new Gson().toJson(game.blackUsername());
      var gameName = new Gson().toJson(game.gameName());
      String gameString = new Gson().toJson(game.game());
      String json = new Gson().toJson(game);

      try (var ps = conn.prepareStatement(statement)) {
        ps.setInt(1, game.gameID());
        ps.setString(2, whiteUsername);
        ps.setString(3, blackUsername);
        ps.setString(4, gameName);
        ps.setString(5, gameString);
        ps.setString(6, json);
        ps.setInt(7, game.gameID());

        ps.executeUpdate();
      }
    } catch (SQLException exception) {
      ErrorData error = new ErrorData("Unable to update game data in the database: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 400);
    }
  }

  @Override
  public GameList listGames() throws DataAccessException{
    var gameList = new ArrayList<GameData>();
   // DatabaseManager databaseManager = new DatabaseManager();
   // databaseManager.configureDatabase();
    try(var conn = DatabaseManager.getConnection()){
      var statement = "Select gameID, json From Game";
      try(var ps = conn.prepareStatement(statement)){
        try(var rs = ps.executeQuery()){
          while(rs.next()){
            var json = rs.getString("json");
            var game = new Gson().fromJson(json, GameData.class);
            gameList.add(game);
          }
        }
      }
    }
    catch (DataAccessException exception){
      ErrorData error = new ErrorData("Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 400);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    GameList games = new GameList(gameList);
    return games;
  }

  @Override
  public void clear() throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
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
    return true;
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
      ErrorData error = new ErrorData("unable to update database: %s, %s");
      throw new DataAccessException(String.format(error.message(), statement, exception.getMessage()), 400);
    }
  }
}
