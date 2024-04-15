package server.websocket;

import com.google.gson.Gson;
import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;

public class Connection {
  public String authtoken;
  public Session session;
  public int gameID;
  public String username;

  public Connection(String authtoken, int gameID, Session session, String username){
    this.authtoken = authtoken;
    this.gameID = gameID;
    this.session = session;
    this.username = username;
  }

  public void send(ServerMessage message) throws IOException{
    session.getRemote().sendString(new Gson().toJson(message));
  }

  public void sendGame(LoadGameMessage loadGameMessage) throws IOException {
    session.getRemote().sendString(new Gson().toJson(loadGameMessage));
  }

  public void sendError(ErrorMessage errorMessage) throws IOException {
    session.getRemote().sendString(new Gson().toJson(errorMessage));
  }
}
