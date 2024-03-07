package dataAccess;

import com.google.gson.Gson;
import model.AuthData;
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
  public AuthData getAuth(String authToken) {
    return null;
  }

  @Override
  public void deleteAuth(String authToken) {

  }

  @Override
  public boolean checkAuth(String authToken) {
    return false;
  }

  @Override
  public void clear() throws DataAccessException{
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
    var statement = "Truncate Auth";
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
