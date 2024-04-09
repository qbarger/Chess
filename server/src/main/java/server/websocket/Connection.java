package server.websocket;

import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
  public String authtoken;
  public Session session;
  public int gameID;

  public Connection(String authtoken, int gameID, Session session){
    this.authtoken = authtoken;
    this.gameID = gameID;
    this.session = session;
  }

  public void send(String message) throws IOException{
    session.getRemote().sendString(message);
  }
}
