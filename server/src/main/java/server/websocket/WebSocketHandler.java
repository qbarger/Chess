package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand userGameCommand= new Gson().fromJson(message, UserGameCommand.class);
    switch(userGameCommand.getCommandType()){
      case LEAVE -> leave(userGameCommand.getAuthString());
      case RESIGN -> resign(userGameCommand.getAuthString());
      case MAKE_MOVE -> makeMove(userGameCommand.getAuthString());
      case JOIN_PLAYER -> joinPlayer(userGameCommand.getAuthString());
      case JOIN_OBSERVER -> joinObserver(userGameCommand.getAuthString());
    }
  }

  private void leave(String authtoken) throws IOException{
    connectionManager.remove(authtoken);
  }

  private void resign(String authtoken){
    connectionManager.remove(authtoken);
  }

  private void makeMove(String authtoken){

  }

  private void joinPlayer(String authtoken){

  }

  private void joinObserver(String authtoken){

  }
}
