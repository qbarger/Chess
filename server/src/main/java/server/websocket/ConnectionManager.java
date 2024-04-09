package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import javax.management.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

  public void add(int gameID, String authtoken, Session session){
    var connection = new Connection(authtoken, gameID, session);
    connections.put(gameID,connection);
  }

  public void remove(int gameID){
    connections.remove(gameID);
  }

  public void broadcast(String authtoken, int gameID, Notification notification) throws IOException {
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()){
      if (c.session.isOpen()){
        if(!c.authtoken.equals(authtoken)) {
          if (c.gameID != gameID) {
            c.send(notification.toString());
          }
        }
      } else {
        removeList.add(c);
      }
    }
    for (var c : removeList){
      connections.remove(c.gameID);
    }
  }
}
