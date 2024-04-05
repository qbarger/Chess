package ui;
import chess.ChessGame;
import chess.ChessMove;
//import org.glassfish.tyrus.core.wsadl.model.Endpoint;
import com.google.gson.Gson;
import javax.websocket.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.management.Notification;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import webSocketMessages.*;

public class WebsocketFacade extends Endpoint implements MessageHandler{
  Session session;
  GameHandler gameHandler;

  public WebsocketFacade(String url, GameHandler gameHandler) throws Exception{
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.gameHandler=gameHandler;
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private void joinPlayer(int gameID, ChessGame.TeamColor playerColor) throws IOException {
    try {
      var action = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, playerColor);
      this.session.getBasicRemote().sendText(new Gson().toJson(action));
    } catch () {

    }
  }

  private void joinObserver(int gameID){

  }

  private void makeMove(int gameID, ChessMove move){

  }

  private void leaveGame(int gameID){

  }

  private void resignGame(int gameID){

  }

  @Override
  public void notify(Notification notification) {

  }

  @Override
  public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

  }
}
