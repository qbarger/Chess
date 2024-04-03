package ui;
import chess.ResponseException;
import spark.Session;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketFacade {
  Session session;
  NotificationHandler notificationHandler;

  public WebsocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException{
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");

    } catch (ResponseException exception){
      throw new ResponseException(exception.getMessage(), 500);
    }
  }
}
