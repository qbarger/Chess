package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

  public void addPlayer(int gameID, String authtoken, Session session, String username){
    var connection = new Connection(authtoken, gameID, session, username, true);
    connections.put(authtoken,connection);
  }

  public void addObserver(int gameID, String authtoken, Session session, String username){
    var connection = new Connection(authtoken, gameID, session, username, false);
    connections.put(authtoken,connection);
  }

  public void remove(String authtoken){
    connections.remove(authtoken);
  }

  public boolean checkResign(){
    boolean check = false;
    for(var c : connections.values()){
      if(c.resign){
        check = true;
        break;
      }
    }
    return check;
  }

  public void setResign(){
    for(var c : connections.values()){
      c.setResignTrue();
    }
  }

  public void broadcast(String authtoken, int gameID, ServerMessage message) throws IOException {
    for (var c : connections.values()){
      if (c.session.isOpen()){
        if(!c.authtoken.equals(authtoken)) {
          if (c.gameID == gameID) {
            c.send(message);
          }
        }
      }
    }
  }

  public void sendMessage(String authtoken, ServerMessage message) throws IOException{
    var connection = connections.get(authtoken);
    connection.send(message);
  }

  public void sendGame(int gameID, String authtoken, LoadGameMessage loadGameMessage) throws IOException{
    for (var c : connections.values()){
      if(c.session.isOpen()) {
        if(c.authtoken.equals(authtoken)) {
          if (c.gameID == gameID) {
            c.sendGame(loadGameMessage);
          } else {
            c.sendError(new ErrorMessage("Error: wrong game ID..."));
          }
        }
      }
    }
  }

  public void sendMove(int gameID, String authtoken, LoadGameMessage loadGameMessage) throws IOException{
    for(var c : connections.values()){
      if(c.session.isOpen()){
        if(!c.authtoken.equals(authtoken)){
          if(c.gameID == gameID){
            c.sendGame(loadGameMessage);
          } else {
            c.sendError(new ErrorMessage("Error: wrong game ID..."));
          }
        }
      }
    }
  }

  public void sendError(String authToken, ErrorMessage errorMessage) throws IOException {
    for(var c : connections.values()){
      if(c.session.isOpen()){
        if(c.authtoken.equals(authToken)){
          c.sendError(errorMessage);
        }
      }
    }
  }
}
