package ui;
import chess.ChessGame;
import chess.ChessMove;
//import org.glassfish.tyrus.core.wsadl.model.Endpoint;
import chess.ResponseException;
import com.google.gson.Gson;
import javax.websocket.*;

import model.GameData;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.management.Notification;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;

public class WebsocketFacade extends Endpoint {
  Session session;
  GameHandler gameHandler;

  public WebsocketFacade(String url, GameHandler gameHandler) throws Exception{
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.gameHandler=gameHandler;
      WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
      this.session = webSocketContainer.connectToServer(this, socketURI);
      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message){
          ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
          switch (serverMessage.getServerMessageType()){
            case NOTIFICATION -> gameHandler.printMessage(new Gson().fromJson(message, NotificationMessage.class));
            case ERROR -> printError(new Gson().fromJson(message, ErrorMessage.class));
            case LOAD_GAME -> loadGame(new Gson().fromJson(message, LoadGameMessage.class));
          }
        }
      });
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public void joinPlayer(String username, int gameID, ChessGame.TeamColor playerColor, String authtoken) throws IOException, ResponseException {
    try {
      var command = new JoinPlayerCommand(authtoken,playerColor,gameID, username);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e) {
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  public void joinObserver(int gameID, String username, String authtoken) throws IOException, ResponseException {
    try {
      var command = new JoinObserverCommand(gameID, username, authtoken);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(), 500);
    }
  }

  public void makeMove(int gameID, ChessMove move, String username, String authtoken, ChessGame.TeamColor color) throws IOException, ResponseException{
    try {
      var command = new MakeMoveCommand(gameID, move, username, authtoken, color);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  public void leaveGame(int gameID, String username, String authtoken) throws IOException, ResponseException{
    try {
      var command = new LeaveGameCommand(gameID, username, authtoken);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  public void resignGame(int gameID, String username, String authtoken) throws IOException, ResponseException{
    try {
      var command = new ResignGameCommand(gameID, username, authtoken);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    } catch (IOException e){
      throw new ResponseException(e.getMessage(),500);
    }
  }

  public void loadGame(LoadGameMessage loadGameMessage){
    gameHandler.updateGame(loadGameMessage.game);
    gameHandler.printBoard(loadGameMessage.game, loadGameMessage.color);
  }

  public void printError(ErrorMessage errorMessage){
    System.out.println(errorMessage.getErrorMessage());
  }

  @Override
  public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {
  }
}
