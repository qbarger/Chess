package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {

  public RegisterService testObject1;
  public LogoutService testObject2;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    testObject1 = new RegisterService(userTestDB,authTestDB);
    testObject2 = new LogoutService(userTestDB,authTestDB);
  }
  @Test
  void logout() throws DataAccessException {
    AuthData auth1 = testObject1.register(new UserData("john","3:16","saint@gmail.com"));
    testObject2.logout(new AuthData("john", auth1.authToken()));

    assertEquals(null,authTestDB.getAuth("john"));
  }

  @Test
  void logoutFails() throws DataAccessException {
    try {
      testObject2.logout(new AuthData("0a0a3c31-8609-4af7-b99c-81ac014e265e", "john"));
      fail("Expected a Data Access Exception to be thrown.");
    }
    catch (DataAccessException d){
      assertEquals("User could not be found.", d.getMessage());
    }
  }
}