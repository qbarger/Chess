package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseAuthDao;
import dataAccess.DatabaseGameDao;
import dataAccess.DatabaseUserDao;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClearTest {
  private DatabaseGameDao databaseGameDao;
  private DatabaseUserDao databaseUserDao;
  private DatabaseAuthDao databaseAuthDao;

  @BeforeEach
  void setup() {
    databaseAuthDao = new DatabaseAuthDao();
    databaseGameDao = new DatabaseGameDao();
    databaseUserDao = new DatabaseUserDao();
  }

  @Test
  void clearUser() throws DataAccessException {
    databaseUserDao.createUser(new UserData("username", "password", "email"));
    databaseUserDao.clear();
    assertEquals(false, databaseUserDao.checkUser("username"));
  }

  @Test
  void clearAuth() throws DataAccessException {
    AuthData auth = databaseAuthDao.createAuth("steve");
    databaseAuthDao.clear();
    assertEquals(null, databaseAuthDao.getAuth(auth.authToken()));
  }
}
