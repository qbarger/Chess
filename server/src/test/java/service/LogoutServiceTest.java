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
  void setup() throws DataAccessException {
    userTestDB = new DatabaseUserDao();
    authTestDB = new DatabaseAuthDao();
    registerService = new RegisterService(userTestDB,authTestDB);
    logoutService = new LogoutService(userTestDB,authTestDB);
  }
  @Test
  void logout() throws DataAccessException {
    userTestDB.clear();
    authTestDB.clear();
    AuthData auth1=registerService.register(new UserData("john", "3:16", "saint@gmail.com"));
    logoutService.logout(auth1.authToken());
    assertThrows(DataAccessException.class, () -> {
      authTestDB.getAuth(auth1.authToken());
    });
  }

  @Test
  void logoutFails() throws DataAccessException {
    try {
      authTestDB.clear();
      logoutService.logout("0a0a3c31-8609-4af7-b99c-81ac014e265e");
      fail("Expected a Data Access Exception to be thrown.");
    }
    catch (DataAccessException error){
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}