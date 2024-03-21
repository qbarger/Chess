package clientTests;

import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryUserDao;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import service.ClearService;
import ui.Prelogin;
import ui.ServerFacade;

import javax.swing.tree.ExpandVetoException;

import static org.junit.jupiter.api.Assertions.*;


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
      fail("Expected a 403");
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
  public void loginFails() throws DataAccessException {
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    try {
      String method = "POST";
      String path = "/session";
      UserData user = new UserData("username", "password", "email");
      AuthData auth = serverFacade.makeRequest(method, null, path, user, AuthData.class);
      fail("Expected a 401");
    } catch (Exception e){
      assertEquals("failure: 401", e.getMessage());
    }
  }

  @Test
  public void logout() throws Exception {
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    String one = "POST";
    String two = "/user";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(one, null, two, user, AuthData.class);

    String method = "DELETE";
    String path = "/session";
    AuthData auth2 = serverFacade.makeRequest(method, auth.authToken(), path, null, AuthData.class);
    assertTrue(auth2.authToken() == null);
  }

  @Test
  public void logoutFails() throws Exception{
    try {
      register();
      String method = "DELETE";
      String path = "/session";
      serverFacade.makeRequest(method, "yuh", path, null, Object.class);
      fail("Expected a 401");
    } catch (Exception e) {
      assertEquals("failure: 401", e.getMessage());
    }
  }

  @Test
  public void create() throws Exception{
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    String method = "POST";
    String one = "/user";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(method, null, one, user, AuthData.class);

    CreateGameData gameData = new CreateGameData("gameName");
    var path = "/game";
    var gameID = serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);
    assertEquals(1, gameID.gameID());
  }

  @Test
  public void createFails() throws Exception{
    try {
      userDao=new DatabaseUserDao();
      authDao=new DatabaseAuthDao();
      gameDao=new DatabaseGameDao();
      clearService=new ClearService(userDao, authDao, gameDao);
      clearService.clear();
      String method="POST";
      String one="/user";
      UserData user=new UserData("username", "password", "email");
      AuthData auth=serverFacade.makeRequest(method, null, one, user, AuthData.class);

      CreateGameData gameData=new CreateGameData("gameName");
      var path="/game";
      var gameID=serverFacade.makeRequest("POST", "spongebob", path, gameData, GameID.class);
      fail("Expected a 401");
    } catch (Exception e){
      assertEquals("failure: 401", e.getMessage());
    }
  }

  @Test
  public void list() throws Exception {
    register();

    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    String method = "POST";
    String one = "/user";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(method, null, one, user, AuthData.class);

    CreateGameData gameData = new CreateGameData("gameName");
    var path = "/game";
    var gameID = serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);

    var game = serverFacade.makeRequest("GET", auth.authToken(), "/game", null, GameList.class);
    assertEquals(1, game.games().size());
  }

  @Test
  public void listFails(){
    try {
      register();

      userDao = new DatabaseUserDao();
      authDao = new DatabaseAuthDao();
      gameDao = new DatabaseGameDao();
      clearService = new ClearService(userDao,authDao,gameDao);
      clearService.clear();
      String method = "POST";
      String one = "/user";
      UserData user = new UserData("username", "password", "email");
      AuthData auth = serverFacade.makeRequest(method, null, one, user, AuthData.class);

      CreateGameData gameData = new CreateGameData("gameName");
      var path = "/game";
      var gameID = serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);

      var game = serverFacade.makeRequest("GET", "yuh", "/game", null, GameList.class);
      fail("Expected a 401");
    } catch (Exception e){
      assertEquals("failure: 401", e.getMessage());
    }
  }

  @Test
  public void join() throws Exception {
    userDao = new DatabaseUserDao();
    authDao = new DatabaseAuthDao();
    gameDao = new DatabaseGameDao();
    clearService = new ClearService(userDao,authDao,gameDao);
    clearService.clear();
    String method = "POST";
    String one = "/user";
    UserData user = new UserData("username", "password", "email");
    AuthData auth = serverFacade.makeRequest(method, null, one, user, AuthData.class);

    CreateGameData gameData = new CreateGameData("gameName");
    var path = "/game";
    var gameID = serverFacade.makeRequest("POST", auth.authToken(), path, gameData, GameID.class);

    JoinGameData joinGameData=new JoinGameData("WHITE", 1);
    var path2="/game";
    var object = serverFacade.makeRequest("PUT", auth.authToken(), path2, joinGameData, Object.class);
    assertNotNull(object);
  }

  @Test
  public void joinFails() throws Exception {
    try {
      create();
      JoinGameData joinGameData=new JoinGameData("WHITE", 1);
      var path2="/game";
      var object = serverFacade.makeRequest("PUT", "bruh", path2, joinGameData, Object.class);
      fail("Expected a 401");
    } catch (Exception e){
      assertEquals("failure: 401", e.getMessage());
    }

  }

  @Test
  public void observe() throws Exception{
    create();

  }

  @Test
  public void observeFails() throws Exception{

  }

  @AfterAll
  static void stopServer() {
    server.stop();
  }

}
