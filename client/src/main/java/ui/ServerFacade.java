package ui;

import chess.ResponseException;
import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.util.Scanner;
import static ui.Prelogin.*;

import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;

public class ServerFacade {

  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl=url;
  }

  public <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (ResponseException ex) {
      throw new ResponseException(ex.getMessage(), 500);
    }
  }


  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody =http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, Exception {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new Exception("failure: " + status);
    }
  }

  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
