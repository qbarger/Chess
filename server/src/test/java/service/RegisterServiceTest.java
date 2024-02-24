package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
  public RegisterService testObject;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    testObject = new RegisterService(userTestDB,authTestDB);
  }

  @Test
  void register() throws DataAccessException {
    String auth = testObject.register(new UserData("qbarger", "bricks9", "brick@gmail.com"));
    AuthData testAuth = authTestDB.getAuth("qbarger");

    assertEquals(auth, testAuth.authToken());
  }

  @Test
  void registerFails() throws DataAccessException {
    try {
      String auth=testObject.register(new UserData("duck", "over10", "duck@gmail.com"));
      String auth2=testObject.register(new UserData("duck", "gambo", "jeff@gmail.com"));
      fail("Expected a DataClassException to be thrown.");
    }
    catch (DataAccessException d){
      assertEquals("Username already exists.", d.getMessage());
    }
  }
}