package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.DatabaseAuthDao;
import dataAccess.DatabaseGameDao;
import dataAccess.DatabaseUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClearTest {
  private DatabaseGameDao databaseGameDao;
  private DatabaseUserDao databaseUserDao;
  private DatabaseAuthDao databaseAuthDao;

  @BeforeEach
  void setup() throws DataAccessException {
    databaseAuthDao = new DatabaseAuthDao();
    databaseGameDao = new DatabaseGameDao();
    databaseUserDao = new DatabaseUserDao();
  }

  @Test
  void clearUser() throws DataAccessException {
    databaseUserDao.clear();
    databaseUserDao.createUser(new UserData("username", "password", "email"));
    databaseUserDao.clear();
    assertEquals(false, databaseUserDao.checkUser("username"));
  }

  @Test
  void clearAuth() throws DataAccessException {
    try {
      databaseAuthDao.clear();
      AuthData auth=databaseAuthDao.createAuth("steve");
      databaseAuthDao.clear();
      assertEquals(null, databaseAuthDao.getAuth(auth.authToken()));
      fail("Expected a data access exception");
    } catch (DataAccessException exception){
      assertEquals("Error: Auth not found.", exception.getMessage());
    }
  }

  @Test
  void clearGame() throws DataAccessException {
    try {
      databaseGameDao.clear();
      databaseGameDao.createGame(new GameData(1, null, null, "game1", new ChessGame()));
      databaseGameDao.clear();
      assertEquals(null, databaseGameDao.getGame(1));
      fail("Expected a data access exception");
    } catch (DataAccessException exception){
      assertEquals("Error: Unable to read data: Error: Game not found.", exception.getMessage());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
