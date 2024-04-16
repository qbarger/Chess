package ui;

import chess.ResponseException;
import model.AuthData;
import model.UserData;

import java.util.Scanner;

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
    if (userInput.equals("help")) {
      help();
    } else if (userInput.equals("register")) {
      AuthData auth = register();
      if(auth != null) {
        Postlogin postlogin=new Postlogin(serverUrl);
        postlogin.run(auth);
      } else {
        help();
      }
    } else if (userInput.equals("login")) {
      AuthData auth = login();
      if(auth != null) {
        Postlogin postlogin=new Postlogin(serverUrl);
        postlogin.run(auth);
      } else {
        help();
      }
    } else {
      System.out.println("Invalid input...");
      return "";
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
      return auth;
    } catch (ResponseException exception){
      System.err.println("Username or password are incorrect...");
    }
    return null;
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
      return auth;
    } catch (ResponseException exception){
      System.err.println("Username already taken. Enter a different username...");
    }
    return null;
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