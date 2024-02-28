package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  public RegisterService testObject0;

  public LoginService testObject1;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup(){
    userTestDB = new MemoryUserDao();
    authTestDB = new MemoryAuthDao();
    testObject1 = new LoginService(userTestDB, authTestDB);
    testObject0 = new RegisterService(userTestDB, authTestDB);
  }
  @Test
  void login() throws DataAccessException {
    AuthData auth0 =testObject0.register(new UserData("Mikal", "bridges", "traded@gmail.com"));
    AuthData auth1 =testObject1.login(new UserData("Mikal", "bridges", "traded@gmail.com"));
    AuthData testAuth = authTestDB.getAuth("Mikal");

    assertEquals(auth1.authToken(), testAuth.authToken());
  }

  @Test
  void loginFails() throws DataAccessException {
    try {
      AuthData auth1 =testObject1.login(new UserData("Mikal", "bridges", "traded@gmail.com"));

      fail("Expected a Data Class Exception to be thrown.");
    }
    catch (DataAccessException d){
      assertEquals("Username does not exist.", d.getMessage());
    }
  }
}