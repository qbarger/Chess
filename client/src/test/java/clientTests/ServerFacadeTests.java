package clientTests;

import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryUserDao;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import service.ClearService;
import ui.Prelogin;
import ui.ServerFacade;

import javax.swing.tree.ExpandVetoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

  private static Server server;
  static ServerFacade serverFacade;
  ClearService clearService;
  UserDao userDao;
  AuthDao authDao;
  GameDao gameDao;

  @BeforeAll
  public static void init() throws DataAccessException {
    server = new Server();
    var port = server.run(8080);
    System.out.println("Started test HTTP server on " + port);
    serverFacade = new ServerFacade("http://localhost:" + Integer.toString(port));
  }

  @Test
  public void register() throws Exception {
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    String method = "POST";
    String path = "/user";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(method, null, path, user, AuthData.class);
  }

  @Test
  public void registerFails() throws Exception {
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    try {
      register();
      String method="POST";
      String path="/user";
      UserData user=new UserData("username", "password", "email");
      AuthData auth=serverFacade.makeRequest(method, null, path, user, AuthData.class);
    } catch (Exception e){
      assertEquals("failure: 403", e.getMessage());
    }
  }

  @Test
  public void login() throws Exception{
    register();
    String method = "POST";
    String path = "/session";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(method, null, path, user, AuthData.class);
    assertTrue(auth.username().equals("username"));
  }

  @Test
  public void loginFails(){

  }

  @AfterAll
  static void stopServer() {
    server.stop();
  }

}
