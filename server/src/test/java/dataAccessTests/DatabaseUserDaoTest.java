package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseUserDao;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUserDaoTest {
  public DatabaseUserDao databaseUserDao;

  @BeforeEach
  void setup() {
    databaseUserDao = new DatabaseUserDao();
  }

  @Test
  void createUser() throws DataAccessException {
    UserData user = new UserData("qbarger", "beebee", "j@g.com");
    databaseUserDao.createUser(user);
    assertEquals(true, databaseUserDao.checkUser("qbarger"));
  }

  @Test
  void createUserFails() throws DataAccessException {
    try {
      UserData user1 = new UserData("qbarger", "beebee", "j@g.com");
      databaseUserDao.createUser(user1);
      UserData user2 = new UserData("qbarger", "beebee", "j@g.com");
      databaseUserDao.createUser(user2);
      fail("Expected an SQL exception");
    } catch (DataAccessException exception) {
      assertEquals("unable to update database: Insert into User (username, password, email, json) Values (?,?,?,?), Duplicate entry 'qbarger' for key 'user.PRIMARY'", exception.getMessage());
    }
  }

  @Test
  void checkUser() {
  }

  @Test
  void checkPassword() throws DataAccessException{
    UserData user = new UserData("username", "password", "email");
    databaseUserDao.createUser(user);
    boolean check = databaseUserDao.checkPassword(user);
    assertEquals(true, check);
  }

  @Test
  void checkPasswordFails() {

  }
}