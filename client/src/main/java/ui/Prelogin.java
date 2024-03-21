package ui;
import chess.ResponseException;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;

public class Prelogin {
  private final String serverUrl;
  private boolean playing;
  ServerFacade serverFacade;

  public Prelogin(String url) {
    playing = true;
    this.serverUrl = url;
    serverFacade = new ServerFacade(url);
  }

  public void run() throws Exception{

    Scanner user = new Scanner(System.in);

    while(playing) {
      System.out.printf("[LOGGED_OUT] >>> ");
      String userInput=user.next();

      if(userInput.equals("quit")){
        System.out.println("Quitting...");
        System.exit(0);
      }
      else {
        eval(userInput);
      }
    }
  }

  public String eval(String userInput) throws Exception {
    try {
      if (userInput.equals("help")) {
        help();
      } else if (userInput.equals("register")) {
        AuthData auth = register();
        Postlogin postlogin = new Postlogin(serverUrl);
        postlogin.run(auth);
        help();
      } else if (userInput.equals("login")) {
        AuthData auth = login();
        Postlogin postlogin = new Postlogin(serverUrl);
        postlogin.run(auth);
        help();
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

  public AuthData login() throws Exception{
    try {
      Scanner userReg=new Scanner(System.in);
      System.out.println("Enter Your Username:");
      String username=userReg.nextLine();
      System.out.println("Enter Your Password:");
      String password=userReg.nextLine();

      UserData user=new UserData(username, password, null);
      var path="/session";
      AuthData auth=serverFacade.makeRequest("POST", null, path, user, AuthData.class);
      System.out.println("Logged in as " + username + "...");
      return auth;
    } catch (ResponseException exception){
      System.out.println("Username or password are incorrect...");
      throw new ResponseException("User not found...", 500);
    }
  }

  public AuthData register() throws Exception {
    try {
      Scanner userReg=new Scanner(System.in);
      System.out.println("Create Your Username:");
      String username=userReg.nextLine();
      System.out.println("Create Your Password:");
      String password=userReg.nextLine();
      System.out.println("Enter Your Email:");
      String email=userReg.nextLine();

      UserData user=new UserData(username, password, email);
      var path="/user";
      AuthData auth=serverFacade.makeRequest("POST", null, path, user, AuthData.class);
      System.out.println("Logged in as " + username + "...");
      return auth;
    } catch (ResponseException exception){
      System.out.println("Username already taken. Choose a different username...");
      throw new ResponseException("User already exists...", 500);
    }
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