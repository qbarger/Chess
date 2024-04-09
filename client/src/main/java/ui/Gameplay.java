package ui;

import model.AuthData;

public class Gameplay {
  WebsocketFacade websocketFacade;


  public Gameplay(WebsocketFacade websocketFacade){
    this.websocketFacade = websocketFacade;
  }

  public void help(){
    System.out.println("redraw - board");
    System.out.println("leave - game");
    System.out.println("make move");
    System.out.println("resign - from the game");
    System.out.println("highlight - legal moves");
  }

  public void redrawChessBoard(){

  }

  public void leave(){

  }

  public void makeMove(){

  }

  public void resign(){

  }

  public void highlightLegalMoves(){

  }

  public void run(AuthData auth){
    System.out.println("Entering gameplay...");

  }
}
