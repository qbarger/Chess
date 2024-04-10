package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;

public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand userGameCommand= new Gson().fromJson(message, UserGameCommand.class);
    switch(userGameCommand.getCommandType()){
      case LEAVE -> leave(userGameCommand.getAuthString(), new Gson().fromJson(message, LeaveGameCommand.class));
      case RESIGN -> resign(userGameCommand.getAuthString(), new Gson().fromJson(message, ResignGameCommand.class));
      case MAKE_MOVE -> makeMove(userGameCommand.getAuthString(), new Gson().fromJson(message, MakeMoveCommand.class));
      case JOIN_PLAYER -> joinPlayer(userGameCommand.getAuthString(), new Gson().fromJson(message, JoinPlayerCommand.class));
      case JOIN_OBSERVER -> joinObserver(userGameCommand.getAuthString(), new Gson().fromJson(message, JoinObserverCommand.class));
    }
  }

  private void leave(String authtoken, LeaveGameCommand cmd) throws IOException{
    var connection = connectionManager.connections.get(authtoken);
    connectionManager.remove(authtoken);
    var text = String.format("%s left the game...", connection.username);
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(authtoken, connection.gameID, alert);
  }

  private void resign(String authtoken, ResignGameCommand cmd) throws IOException {
    var connection = connectionManager.connections.get(authtoken);
    var text = String.format("%s resigned...", connection.username);
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(authtoken, connection.gameID, alert);
  }

  private void makeMove(String authtoken, MakeMoveCommand cmd) throws IOException {
    var connection = connectionManager.connections.get(authtoken);
    var text = String.format("%s made a move...", connection.username);
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(authtoken, connection.gameID, alert);
  }

  private void joinPlayer(String authtoken, JoinPlayerCommand cmd) throws IOException{
    var connection = connectionManager.connections.get(authtoken);
    connectionManager.add(connection.gameID, authtoken, connection.session, connection.username);
    var text = String.format("%s joined as a player...", connection.username);
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(authtoken, connection.gameID, alert);
  }

  private void joinObserver(String authtoken, JoinObserverCommand cmd) throws IOException {
    var connection = connectionManager.connections.get(authtoken);
    connectionManager.add(connection.gameID, authtoken, connection.session, connection.username);
    var text = String.format("%s joined as an observer...", connection.username);
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(authtoken, connection.gameID, alert);
  }
}
