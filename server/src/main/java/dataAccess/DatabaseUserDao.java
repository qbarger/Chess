package dataAccess;

import com.google.gson.Gson;
import model.ErrorData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

import static java.sql.Types.NULL;

public class DatabaseUserDao implements UserDao{

  public DatabaseUserDao() {
    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.configureDatabase();
  }
  @Override
  public void createUser(UserData user) throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    String hashedPassword = storeUserPassword(user.password());
    var statement = "Insert into User (username, password, email, json) Values (?,?,?,?)";
    var json = new Gson().toJson(user);
    executeCommand(statement, user.username(), hashedPassword, user.email(), json);
  }

  @Override
  public boolean checkUser(String username) throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      var statement="Select username From User Where username = ?";
      try (var ps = conn.prepareStatement(statement)){
        ps.setString(1, username);
        try(var rs = ps.executeQuery()){
          return rs.next();
        }
      }
    }
    catch (SQLException exception){
      ErrorData error = new ErrorData("Error: Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 401);
    }
  }

  @Override
  public boolean checkPassword(UserData user) throws DataAccessException{
    if(verifyUser(user)){
      return true;
    }
    else {
      ErrorData error = new ErrorData("Error: Incorrect password: %s");
      throw new DataAccessException(error.message(), 401);
    }
  }

  @Override
  public void clear() throws DataAccessException{
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    var statement = "Truncate User";
    executeCommand(statement);
  }

  private String storeUserPassword(String password) throws DataAccessException{
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPassword = encoder.encode(password);

    // write the hashed password in database along with the user's other information
    return hashedPassword;
  }

  private boolean verifyUser(UserData user) throws DataAccessException{
    // read the previously hashed password from the database
    //DatabaseManager databaseManager = new DatabaseManager();
    //databaseManager.configureDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      var statement="Select password From User Where username = ?";
      try (var ps = conn.prepareStatement(statement)){
        ps.setString(1, user.username());
        try(var rs = ps.executeQuery()){
          if(rs.next()) {
            String password=rs.getString("password");
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            boolean passwordMatches=encoder.matches(user.password(), password);
            return passwordMatches;
          }
          else {
            ErrorData error = new ErrorData("Error: Unable to read data: %s");
            throw new DataAccessException(error.message(), 500);
          }
        }
      }
    }
    catch (SQLException exception){
      ErrorData error = new ErrorData("Error: Unable to read data: %s");
      throw new DataAccessException(String.format(error.message(), exception.getMessage()), 500);
    }
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
      ErrorData error = new ErrorData("Error: unable to update database: %s, %s");
      throw new DataAccessException(String.format(error.message(), statement, exception.getMessage()), 500);
    }
  }
}
