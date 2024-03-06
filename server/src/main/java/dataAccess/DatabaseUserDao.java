package dataAccess;

import com.google.gson.Gson;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import static java.sql.Types.NULL;

public class DatabaseUserDao implements UserDao{
  @Override
  public void createUser(UserData user) throws DataAccessException{
    var statement = "Insert into User (username, password, email) Values (?,?,?)";
    var json = new Gson().toJson(user);
    executeCommand(statement, user.username(), user.password(), user.email(), json);
  }

  @Override
  public boolean checkUser(String username) throws DataAccessException{
    try (var conn = DatabaseManager.getConnection()) {
      var statement="Select username From User Where username = ?";
      try (var ps = conn.prepareStatement(statement)){
        try(var rs = ps.executeQuery()){
          ps.setString(1, username);
          return rs.next();
        }
      }
    }
    catch (SQLException exception){
      throw new DataAccessException(String.format("Unable to read data: %s", exception.getMessage()), 500);
    }
  }

  @Override
  public boolean checkPassword(UserData user) {
    var statement = "Select password From User";
    var json = new Gson().toJson(user.password());
  }

  @Override
  public void clear() throws DataAccessException{
    var statement = "Truncate User";
    executeCommand(statement);
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
