package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {

  public RegisterService registerService;
  public LogoutService logoutService;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    registerService = new RegisterService(userTestDB,authTestDB);
    logoutService = new LogoutService(userTestDB,authTestDB);
  }
  @Test
  void logout() throws DataAccessException {
    AuthData auth1 = registerService.register(new UserData("john","3:16","saint@gmail.com"));
    logoutService.logout(auth1.authToken());

    assertEquals(null,authTestDB.getAuth("john"));
  }

  @Test
  void logoutFails() throws DataAccessException {
    try {
      logoutService.logout("0a0a3c31-8609-4af7-b99c-81ac014e265e");
      fail("Expected a Data Access Exception to be thrown.");
    }
    catch (DataAccessException error){
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}