package server.websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameData;
import model.MakeMoveData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UpdateGameService;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
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
      case JOIN_PLAYER -> checkJoinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class), session);
      case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class), session);
    }
  }

  private void leave(LeaveGameCommand cmd) throws IOException, SQLException, DataAccessException {
    var text = String.format("%s left the game...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    updateGameService.leaveGame(cmd.getGameID(), cmd.getAuthString());
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    connectionManager.sendMessage(cmd.getAuthString(), new NotificationMessage("You left the game..."));
    connectionManager.remove(cmd.getAuthString());
  }

  private void resign(ResignGameCommand cmd) throws IOException, SQLException, DataAccessException {
    var text = String.format("%s resigned...", cmd.getUsername());
    var alert = new NotificationMessage(text);
    for(var c : connectionManager.connections.values()){
      if(c.authtoken.equals(cmd.getAuthString())){
        if(!c.resign) {
          if (c.player) {
            connectionManager.setResign();
            connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
            connectionManager.sendMessage(cmd.getAuthString(), new NotificationMessage("You resigned..."));
          } else {
            connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Observers cannot resign..."));
          }
        } else {
          connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Observers cannot resign..."));
        }
      }
    }
  }

  private void makeMove(MakeMoveCommand cmd) throws IOException, DataAccessException, InvalidMoveException {
    try {
      if(!connectionManager.checkResign()) {
        MakeMoveData moveData=new MakeMoveData(cmd.getGameID(), cmd.getMove(), cmd.getColor());
        GameData game=updateGameService.makeMove(moveData, cmd.getAuthString());
        if (game.game().isInCheckmate(game.game().getTeamTurn())) {
          if (cmd.getUsername().equals(game.whiteUsername())) {
            connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), new NotificationMessage(cmd.getUsername() + " has checkmated " + game.blackUsername()));
          } else {
            connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), new NotificationMessage(cmd.getUsername() + " has checkmated " + game.whiteUsername()));
          }
        } else if (game.game().isInStalemate(game.game().getTeamTurn())) {
          connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), new NotificationMessage(game.whiteUsername() + " stalemated " + game.blackUsername()));
        } else {
          var text=String.format("%s made a move...", cmd.getUsername());
          var alert=new NotificationMessage(text);
          connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
          connectionManager.sendGame(cmd.getGameID(), cmd.getAuthString(), new LoadGameMessage(text, game.game(), cmd.getColor()));
          connectionManager.sendMove(cmd.getGameID(), cmd.getAuthString(), new LoadGameMessage(text, game.game(), cmd.getColor()));
        }
      } else {
        connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: cannot make move..."));
      }
    } catch (DataAccessException exception) {
      connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: invalid move..."));
    }
    }

  private void checkJoinPlayer(JoinPlayerCommand cmd, Session session) throws IOException, DataAccessException {
    try {
      connectionManager.addPlayer(cmd.getGameID(), cmd.getAuthString(), session, cmd.getUsername());
      GameData gameData=updateGameService.gameDB.getGame(cmd.getGameID());
      cmd.setUsername(updateGameService.authDB.getAuth(cmd.getAuthString()).username());
      if(gameData != null) {
        if (cmd.getTeamColor() == ChessGame.TeamColor.WHITE) {
          if (gameData.whiteUsername() == null) {
            connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
          } else {
            if (gameData.whiteUsername().equals(cmd.getUsername())) {
              joinPlayer(cmd, gameData);
            } else {
              connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
            }
          }
        }
        if (cmd.getTeamColor() == ChessGame.TeamColor.BLACK) {
          if (gameData.blackUsername() == null) {
            connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
          } else {
            if (gameData.blackUsername().equals(cmd.getUsername())) {
              joinPlayer(cmd, gameData);
            } else {
              connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
            }
          }
        }
      } else {
        connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
      }
    } catch (DataAccessException exception) {
      connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: Cannot join that team..."));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void joinPlayer(JoinPlayerCommand cmd, GameData gameData) throws IOException, DataAccessException {
    var text=String.format("%s joined as %s...", cmd.getUsername(), cmd.getTeamColor().toString());
    var alert=new NotificationMessage(text);
    connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
    connectionManager.sendGame(cmd.getGameID(), cmd.getAuthString(), new LoadGameMessage("You joined the game...", gameData.game(), cmd.getTeamColor()));
  }

  private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException, DataAccessException {
    try {
      connectionManager.addObserver(cmd.getGameID(), cmd.getAuthString(), session, cmd.getUsername());
      boolean check = updateGameService.authDB.checkAuth(cmd.getAuthString());
      if(check) {
        GameData gameData=updateGameService.gameDB.getGame(cmd.getGameID());
        if (gameData != null) {
          var text=String.format("%s joined as an observer...", cmd.getUsername());
          var alert=new NotificationMessage(text);
          connectionManager.broadcast(cmd.getAuthString(), cmd.getGameID(), alert);
          connectionManager.sendGame(cmd.getGameID(), cmd.getAuthString(), new LoadGameMessage(text, gameData.game(), null));
        } else {
          connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: bad game ID..."));
        }
      } else {
        connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: no authorization..."));
      }
    } catch (DataAccessException exception){
      connectionManager.sendError(cmd.getAuthString(), new ErrorMessage("Error: cannot join that game..."));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
