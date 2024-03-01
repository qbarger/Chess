package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  public RegisterService registerService;

  public LoginService loginService;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    loginService = new LoginService(userTestDB, authTestDB);
    registerService = new RegisterService(userTestDB, authTestDB);
  }
  @Test
  void login() throws DataAccessException {
    AuthData auth0 =registerService.register(new UserData("Mikal", "bridges", "traded@gmail.com"));
    AuthData auth1 =loginService.login(new UserData("Mikal", "bridges", "traded@gmail.com"));
    AuthData testAuth = authTestDB.getAuth(auth1.authToken());

    assertEquals(auth1.authToken(), testAuth.authToken());
  }

  @Test
  void loginFails() throws DataAccessException {
    try {
      AuthData auth1 =loginService.login(new UserData("Mikal", "bridges", "traded@gmail.com"));

      fail("Expected a Data Class Exception to be thrown.");
    }
    catch (DataAccessException error){
      assertEquals("Error: unauthorized", error.getMessage());
    }
  }
}