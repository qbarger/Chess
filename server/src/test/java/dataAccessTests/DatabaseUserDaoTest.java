package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseUserDao;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUserDaoTest {
  public DatabaseUserDao databaseUserDao;

  @BeforeEach
  void setup() throws DataAccessException {
    databaseUserDao = new DatabaseUserDao();
  }

  @Test
  void createUser() throws DataAccessException {
    databaseUserDao.clear();
    UserData user = new UserData("qbarger", "beebee", "j@g.com");
    databaseUserDao.createUser(user);
    assertEquals(true, databaseUserDao.checkUser("qbarger"));
  }

  @Test
  void createUserFails() throws DataAccessException {
    try {
      databaseUserDao.clear();
      UserData user1 = new UserData("qbarger", "beebee", "j@g.com");
      databaseUserDao.createUser(user1);
      UserData user2 = new UserData("qbarger", "beebee", "j@g.com");
      databaseUserDao.createUser(user2);
      fail("Expected an SQL exception");
    } catch (DataAccessException exception) {
      assertNotEquals("unable to update database: Insert into User (username, password, email, json) Values (?,?,?,?), Duplicate entry 'qbarger' for key 'user.PRIMARY'", exception.getMessage());
    }
  }

  @Test
  void checkUser() throws DataAccessException{
    databaseUserDao.clear();
    UserData user = new UserData("john", "jinglebells", "email.com");
    databaseUserDao.createUser(user);
    assertTrue(databaseUserDao.checkUser(user.username()));
  }

  @Test
  void checkUserFails() throws DataAccessException{
    databaseUserDao.clear();
    UserData user = new UserData("james", "bdksjfh", "let.com");
    databaseUserDao.createUser(user);
    assertFalse(databaseUserDao.checkUser("jeffery"));
  }

  @Test
  void checkPassword() throws DataAccessException{
    databaseUserDao.clear();
    UserData user = new UserData("username", "password", "email");
    databaseUserDao.createUser(user);
    boolean check = databaseUserDao.checkPassword(user);
    assertEquals(true, check);
  }

  @Test
  void checkPasswordFails() throws DataAccessException{
    try {
      databaseUserDao.clear();
      UserData user=new UserData("username", "password", "email");
      databaseUserDao.createUser(user);
      databaseUserDao.checkPassword(new UserData("username", "pass", "email"));
      fail("Expected a DataAccess Exception");
    } catch (DataAccessException exception){
      assertEquals("Error: Incorrect password: %s", exception.getMessage());
    }

  }
}