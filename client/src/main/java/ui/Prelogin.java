package ui;
import chess.ResponseException;
import com.google.gson.Gson;
import model.UserData;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;

public class Prelogin {
  private final String serverUrl;
  ServerFacade serverFacade;

  public Prelogin(String url) {
    this.serverUrl = url;
    serverFacade = new ServerFacade(url);
  }

  public void run() throws Exception{

    System.out.println("Welcome to 240 chess. Type Help to get started...");

    Scanner user = new Scanner(System.in);
    String userInput;

    while(true) {
      userInput=user.nextLine();

      if(userInput.equals("quit")){
        System.out.println("Quitting...");
        break;
      }
      else {
        eval(userInput);
      }

      /*
      if (userInput.equals("help")) {
        help();
      } else if (userInput.equals("register")) {
        register(serverUrl);
      } else if (userInput.equals("login")) {
        login();
      } else if (userInput.equals("quit")) {
        quit();
        break;
      }
       */
    }

    user.close();
  }

  public String eval(String userInput) throws Exception {
    try {
      if (userInput.equals("help")) {
        help();
      } else if (userInput.equals("register")) {
        register();
      } else if (userInput.equals("login")) {
        login();
      }
    } catch (ResponseException ex) {
      return ex.getMessage();
    }
    return "";
  }
  public void help(){
    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
    System.out.println("login <USERNAME> <PASSWORD> - to play chess");
    System.out.println("quit - playing chess");
    System.out.println("help - with possible commands");
  }

  public void login() throws ResponseException{
    Scanner userLog = new Scanner(System.in);
    System.out.println("Enter Your Username:");
    String username = userLog.nextLine();
    System.out.println("Enter Your Password:");
    String password = userLog.nextLine();

    try {
      URL url = new URL("http://localhost:8080/session");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
    } catch (Exception exception){
      exception.printStackTrace();
    }
  }

  public void register() throws Exception {
    Scanner userReg = new Scanner(System.in);
    System.out.println("Create Your Username:");
    String username = userReg.nextLine();
    System.out.println("Create Your Password:");
    String password = userReg.nextLine();
    System.out.println("Enter Your Email:");
    String email = userReg.nextLine();

    UserData user = new UserData(username, password, email);
    var path = "/user";
    serverFacade.makeRequest("POST", path, user, UserData.class);
  }
}

/*
        try {
        URL url = new URL("http://localhost:8080");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        UserData user = new UserData(username, password, email);
        String jsonUser = new Gson().toJson(user);

        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
        dataOutputStream.writeBytes(jsonUser);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
        response.append(line);
        }

        reader.close();
        System.out.println("Response from API: " + response.toString());
        connection.disconnect();
        } catch (Exception exception){
        exception.printStackTrace();
        }

 */