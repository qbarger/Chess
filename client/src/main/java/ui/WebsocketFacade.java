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

public class WebsocketFacade extends Endpoint implements GameHandler{
  Session session;
  GameHandler gameHandler;

  public WebsocketFacade(String url, GameHandler gameHandler) throws Exception{
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.gameHandler=gameHandler;
      WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
      this.session = webSocketContainer.connectToServer(this, socketURI);
      this.session.addMessageHandler((javax.websocket.MessageHandler.Whole<String>) message -> {
        ServerMessage message1 = new Gson().fromJson(message, ServerMessage.class);
        gameHandler.printMessage(message1.toString());
      });
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private void joinPlayer(int gameID, ChessGame.TeamColor playerColor, String authtoken, String username) throws IOException, ResponseException {
    try {
      var command = new JoinPlayerCommand(authtoken,playerColor,gameID, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e) {
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  private void joinObserver(int gameID, String username) throws IOException, ResponseException {
    try {
      var command = new JoinObserverCommand(gameID, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  private void makeMove(int gameID, ChessMove move, String username) throws IOException, ResponseException{
    try {
      var command = new MakeMoveCommand(gameID, move, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  private void leaveGame(int gameID, String username) throws IOException, ResponseException{
    try {
      var command = new LeaveGameCommand(gameID, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  private void resignGame(int gameID, String username) throws IOException, ResponseException{
    try {
      var command = new ResignGameCommand(gameID, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  @Override
  public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

  }


  @Override
  public void updateGame(ChessGame game) {

  }

  @Override
  public void printMessage(String message) {
    System.out.println(message);
  }

  public void onMessage(String message){

  }
}
