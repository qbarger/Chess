package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseAuthDao;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAuthDaoTest {
  private DatabaseAuthDao databaseAuthDao;

  @BeforeEach
  void setup() {
    databaseAuthDao = new DatabaseAuthDao();
  }

  @Test
  void createAuth() throws DataAccessException {
    databaseAuthDao.clear();
    AuthData auth = databaseAuthDao.createAuth("username");
    assertTrue(databaseAuthDao.checkAuth(auth.authToken()));
  }

  @Test
  void createAuthFails() throws DataAccessException{
    databaseAuthDao.clear();
    AuthData auth=databaseAuthDao.createAuth("username");
    assertEquals(false, databaseAuthDao.checkAuth(auth.authToken() + "a"));
  }

  @Test
  void getAuth() {

  }

  @Test
  void getAuthFails() {

  }

  @Test
  void deleteAuth() throws DataAccessException{
    databaseAuthDao.clear();
    AuthData auth = databaseAuthDao.createAuth("username");
    databaseAuthDao.deleteAuth(auth.authToken());
    assertFalse(databaseAuthDao.checkAuth(auth.authToken()));
  }

  @Test
  void deleteAuthFails() throws DataAccessException{
    databaseAuthDao.clear();
    AuthData auth=databaseAuthDao.createAuth("username");
    databaseAuthDao.deleteAuth(auth.authToken());
    assertEquals(false, databaseAuthDao.checkAuth(auth.authToken()));
  }

  @Test
  void checkAuth() throws DataAccessException{
    databaseAuthDao.clear();
    AuthData auth=databaseAuthDao.createAuth("username");
    assertEquals(true, databaseAuthDao.checkAuth(auth.authToken()));
  }

  @Test
  void checkAuthFails() throws DataAccessException{
    databaseAuthDao.clear();
    AuthData auth=databaseAuthDao.createAuth("dove");
    boolean check = databaseAuthDao.checkAuth(auth.authToken() + "a");
    assertEquals(false, check);
  }

  @Test
  void isItEmpty() {
  }
}