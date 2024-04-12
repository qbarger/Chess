package server.websocket;

import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameData;
import model.MakeMoveData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UpdateGameService;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.sql.SQLException;

@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();
  private UpdateGameService updateGameService;

  public WebSocketHandler(UpdateGameService updateGameService){this.updateGameService = updateGameService;}

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException, SQLException {
    UserGameCommand userGameCommand= new Gson().fromJson(message, UserGameCommand.class);
    switch(userGameCommand.getCommandType()){
      case LEAVE -> leave(new Gson().fromJson(message, LeaveGameCommand.class));
      case RESIGN -> resign(new Gson().fromJson(message, ResignGameCommand.class));
      case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class));
      case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class), session);
      case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class), session);
    }
  }

  private void leave(LeaveGameCommand cmd) throws IOException, SQLException, DataAccessException {
    connectionManager.remove(cmd.getAuthString());
    var text = String.format("%s left the game...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    updateGameService.leaveGame(cmd.getGameID(), cmd.getAuthString());
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    connectionManager.sendMessage(cmd.getAuthString(), new NotificationMessage("You left the game..."));
  }

  private void resign(ResignGameCommand cmd) throws IOException {
    var text = String.format("%s resigned...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    connectionManager.sendMessage(cmd.getAuthString(), new NotificationMessage("You resigned..."));
  }

  private void makeMove(MakeMoveCommand cmd) throws IOException, DataAccessException, InvalidMoveException {
    MakeMoveData moveData = new MakeMoveData(cmd.getGameID(), cmd.getMove());
    updateGameService.makeMove(moveData, cmd.getAuthString());
    var text = String.format("%s made a move...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    GameData game = updateGameService.gameDB.getGame(cmd.getGameID());
    connectionManager.sendMessage(cmd.getAuthString(), new LoadGameMessage("Move made...", game.game()));
  }

  private void joinPlayer(JoinPlayerCommand cmd, Session session) throws IOException, DataAccessException {
    connectionManager.add(cmd.getGameID(), cmd.getAuthString(), session, cmd.getUsername());
    var text = String.format("%s joined as %s...", cmd.getUsername(), cmd.getTeamColor().toString());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    GameData gameData = updateGameService.gameDB.getGame(cmd.getGameID());
    connectionManager.sendMessage(cmd.getAuthString(), new LoadGameMessage("You joined the game...", gameData.game()));
  }

  private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException, DataAccessException {
    connectionManager.add(cmd.getGameID(), cmd.getAuthString(), session, cmd.getUsername());
    var text = String.format("%s joined as an observer...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    GameData gameData = updateGameService.gameDB.getGame(cmd.getGameID());
    connectionManager.sendMessage(cmd.getAuthString(), new LoadGameMessage("You joined as an observer...", gameData.game()));
  }
}
