package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

  public void add(int gameID, String authtoken, Session session, String username){
    var connection = new Connection(authtoken, gameID, session, username);
    connections.put(authtoken,connection);
  }

  public void remove(String authtoken){
    connections.remove(authtoken);
  }

  public void broadcast(String authtoken, int gameID, ServerMessage message) throws IOException {
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()){
      if (c.session.isOpen()){
        if(!c.authtoken.equals(authtoken)) {
          if (c.gameID == gameID) {
            c.send(message);
          }
        }
      } else {
        removeList.add(c);
      }
    }
    for (var c : removeList){
      connections.remove(c.authtoken);
    }
  }

  public void sendMessage(String authtoken, ServerMessage message) throws IOException{
    var connection = connections.get(authtoken);
    connection.send(message);
  }

  public void sendGame(int gameID, String authtoken, LoadGameMessage loadGameMessage) throws IOException{
    var connection = connections.get(authtoken);
    connection.sendGame(loadGameMessage);
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()){
      if (c.session.isOpen()){
        if (c.gameID == gameID) {
          c.sendGame(loadGameMessage);
        }
      } else {
        removeList.add(c);
      }
    }
    for (var c : removeList){
      connections.remove(c.authtoken);
    }
  }
}
