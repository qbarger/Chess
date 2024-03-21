package clientTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;
import ui.Prelogin;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

  private static Server server;
  static ServerFacade serverFacade;
  static Prelogin prelogin;

  @BeforeAll
  public static void init() throws DataAccessException {
    server = new Server();
    var port = server.run(8080);
    System.out.println("Started test HTTP server on " + port);
    serverFacade = new ServerFacade(Integer.toString(port));
    prelogin = new Prelogin(Integer.toString(port));
  }

  @AfterAll
  static void stopServer() {
    server.stop();
  }


  @Test
  public void login() throws Exception{
    var AuthData = prelogin.login();
    assertTrue(AuthData.username().equals("qbarger"));
  }

  @Test
  public void loginFails(){

  }

  @Test
  public void register(){

  }

  @Test
  public void registerFails(){

  }

}
