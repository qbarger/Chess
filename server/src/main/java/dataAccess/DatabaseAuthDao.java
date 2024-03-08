package dataAccess;

import com.google.gson.Gson;
import model.AuthData;
import model.ErrorData;
import model.UserData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static java.sql.Types.NULL;

public class DatabaseAuthDao implements AuthDao{
  @Override
  public AuthData createAuth(String username) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    String authToken =UUID.randomUUID().toString();
    var statement = "Insert into Auth (username, authToken, json) Values (?,?,?)";
    AuthData auth = new AuthData(username, authToken);
    var json = new Gson().toJson(auth);
    executeCommand(statement, username, authToken, json);
    return auth;
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    try(var conn = DatabaseManager.getConnection()){
      var statement = "Select authToken, json from Auth Where authToken = ?";
      try(var ps = conn.prepareStatement(statement)){
        ps.setString(1, authToken);
        try(var rs = ps.executeQuery()){
          if(rs.next()){
            var json = rs.getString("json");
            var auth = new Gson().fromJson(json, AuthData.class);
            return auth;
          }
          else {
            ErrorData error = new ErrorData("Auth not found.");
            throw new DataAccessException(error.message(), 500);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Delete From Auth Where authToken = ?";
    executeCommand(statement, authToken);
  }

  @Override
  public boolean checkAuth(String authToken) throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      var statement="Select authToken From Auth Where authToken = ?";
      try (var ps = conn.prepareStatement(statement)){
        ps.setString(1, authToken);
        try(var rs = ps.executeQuery()){
          return rs.next();
        }
      }
    }
    catch (SQLException exception){
      ErrorData error = new ErrorData("Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 500);
    }
  }

  @Override
  public void clear() throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Truncate Auth";
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
          } else if (param instanceof AuthData u) {
            ps.setString(i + 1, u.toString());
          } else if (param == null) {
            ps.setNull(i + 1, NULL);
          }
        }
        ps.executeUpdate();
      }
    }
    catch (SQLException exception) {
      ErrorData error= new ErrorData("unable to update database: %s, %s");
      throw new DataAccessException(String.format(error.message(), statement, exception.getMessage()), 500);
    }
  }
}
