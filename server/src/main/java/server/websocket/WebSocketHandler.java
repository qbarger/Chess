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
      case LEAVE -> leave(new Gson().fromJson(message, LeaveGameCommand.class));
      case RESIGN -> resign(new Gson().fromJson(message, ResignGameCommand.class));
      case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class));
      case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class), session);
      case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class), session);
    }
  }

  private void leave(LeaveGameCommand cmd) throws IOException{
    connectionManager.remove(cmd.getAuthtoken());
    var text = String.format("%s left the game...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthtoken(), cmd.getGameID(), alert);
  }

  private void resign(ResignGameCommand cmd) throws IOException {
    var text = String.format("%s resigned...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthtoken(), cmd.getGameID(), alert);
  }

  private void makeMove(MakeMoveCommand cmd) throws IOException {
    var text = String.format("%s made a move...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthtoken(), cmd.getGameID(), alert);
  }

  private void joinPlayer(JoinPlayerCommand cmd, Session session) throws IOException{
    connectionManager.add(cmd.getGameID(), cmd.getAuthtoken(), session, cmd.getUsername());
    var text = String.format("%s joined as %s...", cmd.getUsername(), cmd.getTeamColor().toString());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthtoken(), cmd.getGameID(), alert);
  }

  private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException {
    connectionManager.add(cmd.getGameID(), cmd.getAuthtoken(), session, cmd.getUsername());
    var text = String.format("%s joined as an observer...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthtoken(), cmd.getGameID(), alert);
  }
}
