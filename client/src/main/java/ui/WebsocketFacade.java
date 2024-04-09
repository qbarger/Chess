package ui;
import chess.ChessGame;
import chess.ChessMove;
//import org.glassfish.tyrus.core.wsadl.model.Endpoint;
import chess.ResponseException;
import com.google.gson.Gson;
import javax.websocket.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

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
      this.session.addMessageHandler((javax.websocket.MessageHandler.Whole<String>) message -> {
        Notification notification = new Gson().fromJson(message, Notification.class);
        gameHandler.notify(notification);
      });
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private void joinPlayer(int gameID, ChessGame.TeamColor playerColor, String authtoken) throws IOException, ResponseException {
    try {
      var command = new JoinPlayerCommand(authtoken,playerColor,gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e) {
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  private void joinObserver(int gameID) throws IOException, ResponseException {
    try {
      var command = new JoinObserverCommand(gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  private void makeMove(int gameID, ChessMove move) throws IOException, ResponseException{
    try {
      var command = new MakeMoveCommand(gameID, move);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  private void leaveGame(int gameID) throws IOException, ResponseException{
    try {
      var command = new LeaveGameCommand(gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  private void resignGame(int gameID) throws IOException, ResponseException{
    try {
      var command = new ResignGameCommand(gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  @Override
  public void notify(Notification notification) {

  }

  @Override
  public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

  }


}
