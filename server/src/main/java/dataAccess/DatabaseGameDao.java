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

  public DatabaseGameDao() throws DataAccessException {
    DatabaseManager databaseManager=new DatabaseManager();
    databaseManager.configureDatabase();
  }
  @Override
  public int createGame(GameData game) throws DataAccessException{
    //DatabaseManager databaseManager=new DatabaseManager();
    //databaseManager.configureDatabase();
    var statement="Insert into Game (gameID, whiteUsername, blackUsername, gameName, game, json) Values (?,?,?,?,?,?)";
    GameData newGame = new GameData(getMaxID() + 1, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
    var json=new Gson().toJson(newGame);
    var gameString=new Gson().toJson(newGame.game());
    executeCommand(statement, newGame.gameID(), newGame.whiteUsername(), newGame.blackUsername(), newGame.gameName(), gameString, json);
    return newGame.gameID();
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
            ErrorData error = new ErrorData("Error: Game not found.");
            throw new DataAccessException(error.message(), 400);
          }
        }
      }
    } catch (DataAccessException exception) {
      ErrorData error = new ErrorData("Error: Unable to read data: %s");
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
      ErrorData error = new ErrorData("Error: Unable to update game data in the database: %s");
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
      ErrorData error = new ErrorData("Error: Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 400);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    GameList games = new GameList(gameList);
    return games;
  }

  @Override
  public void leaveGame(int gameID, String username) throws DataAccessException, SQLException {
    try (var conn=DatabaseManager.getConnection()) {
      GameData game=getGame(gameID);
      if (game.whiteUsername().equals(username)) {
        GameData newGame = new GameData(game.gameID(), null, game.blackUsername(), game.gameName(), game.game());
        String statement = "UPDATE Game SET gameID = ?, whiteUsername = NULL, blackUsername = ?, gameName = ?, game = ?, json = ? WHERE gameID = ?";
        var blackUsername = new Gson().toJson(newGame.blackUsername());
        var gameName = new Gson().toJson(newGame.gameName());
        String gameString = new Gson().toJson(newGame.game());
        String json = new Gson().toJson(newGame);
        try (var ps=conn.prepareStatement(statement)) {
          ps.setInt(1, newGame.gameID());
          ps.setString(2, blackUsername);
          ps.setString(3, gameName);
          ps.setString(4, gameString);
          ps.setString(5, json);
          ps.setInt(6, newGame.gameID());

          ps.executeUpdate();
        }
      } else {
        GameData newGame = new GameData(game.gameID(), game.whiteUsername(), null, game.gameName(), game.game());
        String statement = "UPDATE Game SET gameID = ?, whiteUsername = NULL, blackUsername = ?, gameName = ?, game = ?, json = ? WHERE gameID = ?";
        var whiteUsername = new Gson().toJson(newGame.whiteUsername());
        var gameName = new Gson().toJson(newGame.gameName());
        String gameString = new Gson().toJson(newGame.game());
        String json = new Gson().toJson(newGame);
        try (var ps=conn.prepareStatement(statement)) {
          ps.setInt(1, newGame.gameID());
          ps.setString(2, whiteUsername);
          ps.setString(3, gameName);
          ps.setString(4, gameString);
          ps.setString(5, json);
          ps.setInt(6, newGame.gameID());

          ps.executeUpdate();
        } catch (SQLException e) {
          throw new DataAccessException(e.getMessage(), 400);
        }
      }
    }
  }

  @Override
  public GameData makeMove(int gameID, ChessGame game) throws DataAccessException {
    GameData gameData = getGame(gameID);
    try(var conn = DatabaseManager.getConnection()){
      GameData newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game);
      var statement = "UPDATE Game Set game = ?, json = ? Where gameID = ?";
      var gamestring = new Gson().toJson(newGame.game());
      var json = new Gson().toJson(newGame);
      try(var ps = conn.prepareStatement(statement)){
        ps.setString(1, new Gson().toJson(gamestring));
        ps.setString(2, new Gson().toJson(json));
        ps.setInt(3, gameID);
        ps.executeUpdate();
        return gameData;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

    @Override
  public void clear() throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    var statement = "Truncate Game";
    executeCommand(statement);
  }

  @Override
  public int getMaxID() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      var statement="SELECT MAX(gameID) AS max_value FROM Game";
      try (var ps=conn.prepareStatement(statement)) {
        try (var rs=ps.executeQuery()) {
          if (rs.next()) {
            var maxID=rs.getInt("max_value");
            return maxID;
          } else {
            ErrorData error=new ErrorData("Error: Game not found.");
            throw new DataAccessException(error.message(), 400);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
      ErrorData error = new ErrorData("Error: unable to update database: %s, %s");
      throw new DataAccessException(String.format(error.message(), statement, exception.getMessage()), 400);
    }
  }
}
