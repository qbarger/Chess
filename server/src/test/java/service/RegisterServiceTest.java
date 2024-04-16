package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RegisterServiceTest {
  public RegisterService registerService;
  public UserDao userTestDB;
  public AuthDao authTestDB;

  @BeforeEach
  void setup() throws DataAccessException {
    userTestDB = new DatabaseUserDao();
    authTestDB = new DatabaseAuthDao();
    registerService = new RegisterService(userTestDB,authTestDB);
  }

  @Test
  void register() throws DataAccessException {
    AuthData auth = registerService.register(new UserData("gersih", "bricks9", "brick@gmail.com"));
    AuthData testAuth = authTestDB.getAuth(auth.authToken());

    assertEquals(auth.authToken(), testAuth.authToken());
  }

  @Test
  void registerFails() throws DataAccessException {
    try {
      AuthData auth=registerService.register(new UserData("duck", "over10", "duck@gmail.com"));
      AuthData auth2=registerService.register(new UserData("duck", "gambo", "jeff@gmail.com"));
      fail("Expected a DataClassException to be thrown.");
    }
    catch (DataAccessException error){
      assertEquals("Error: already taken", error.getMessage());
    }
  }
}