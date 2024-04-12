package ui;

import chess.ChessGame;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;

import javax.management.Notification;
import javax.websocket.Session;
import java.io.IOException;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.BLACK_ROOK;

public class GameHandler {
  ChessGame game;
  Session session;

  public void updateGame(ChessGame game){
    this.game = game;
  }

  public void loadGame(LoadGameMessage loadGameMessage){
    updateGame(loadGameMessage.game);
    printBoard(loadGameMessage.game);
  }

  public void printMessage(NotificationMessage message){
    System.out.println();
    System.out.println();
    System.out.println(message.getMessage());
    System.out.print("[LOGGED_IN] >>> ");
  }

  public void printBoard(ChessGame game){

  }

  public void makeBoardTop(){
    int size = 10;
    String space;
    String piece="";
    for(int row = 0; row < size; row++){
      for(int col = 0; col < size; col++){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
        }
        else {
          space = SET_BG_COLOR_LIGHT_GREY;
        }
        System.out.print(space);
        if(col == 0 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if (col == 9 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if(row == 0 && col != 0 && col != 9){
          piece = "";
          printLettersTop(col);
        } else if(row == 1){
          piece = "";
          printPiecesWhiteTop(col);
        } else if (row == 2){
          piece = WHITE_PAWN;
        } else if (row == 7) {
          piece = BLACK_PAWN;
        } else if (row == 8) {
          piece = "";
          printPiecesBlackBottom(col);
        } else if (row == 9 && col != 0 && col != 9) {
          piece = "";
          printLettersBottom(col);
        } else {
          piece = EMPTY;
        }

        System.out.print(piece);
        space= RESET_BG_COLOR;
      }
      space = RESET_BG_COLOR;
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void makeBoardBottom(){
    int size = 10;
    String space = "";
    String piece = "";

    for(int row = size - 1; row >= 0; row--){
      for(int col = size - 1; col >= 0; col--){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
        }
        else {
          space = SET_BG_COLOR_LIGHT_GREY;
        }
        System.out.print(space);
        if(col == 0 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if (col == 9 && row != 0 && row != 9){
          piece = "";
          printNumbers(row);
        } else if(row == 0 && col != 0 && col != 9){
          piece = "";
          printLettersTop(col);
        } else if(row == 1){
          piece = "";
          printPiecesWhiteTop(col);
        } else if (row == 2){
          piece = WHITE_PAWN;
        } else if (row == 7) {
          piece = BLACK_PAWN;
        } else if (row == 8) {
          piece = "";
          printPiecesBlackBottom(col);
        } else if (row == 9 && col != 0 && col != 9) {
          piece = "";
          printLettersBottom(col);
        } else {
          piece = EMPTY;
        }

        System.out.print(piece);
        space= RESET_BG_COLOR;
      }
      space = RESET_BG_COLOR;
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void printNumbers(int row){
    String number = EMPTY;
    if(row == 1){
      number = " 1 ";
    } else if (row == 2) {
      number = " 2 ";
    } else if (row == 3){
      number = " 3 ";
    } else if (row == 4) {
      number = " 4 ";
    } else if (row == 5){
      number = " 5 ";
    } else if (row == 6) {
      number = " 6 ";
    } else if (row == 7) {
      number = " 7 ";
    } else if (row == 8) {
      number = " 8 ";
    }
    System.out.print(number);
  }

  public void printLettersTop(int col){
    String letter = EMPTY;
    if(col == 1) {
      letter=" H ";
    } else if(col == 2){
      letter=" G ";
    } else if (col == 3) {
      letter=" F ";
    } else if (col == 4) {
      letter=" E ";
    } else if (col == 5) {
      letter=" D ";
    } else if (col == 6) {
      letter=" C ";
    } else if (col == 7){
      letter=" B ";
    } else if (col == 8) {
      letter=" A ";
    }
    System.out.print(letter);
  }

  public void printLettersBottom(int col){
    String letter = EMPTY;
    if(col == 1) {
      letter=" H ";
    } else if(col == 2){
      letter=" G ";
    } else if (col == 3) {
      letter=" F ";
    } else if (col == 4) {
      letter=" E ";
    } else if (col == 5) {
      letter=" D ";
    } else if (col == 6) {
      letter=" C ";
    } else if (col == 7){
      letter=" B ";
    } else if (col == 8) {
      letter=" A ";
    }
    System.out.print(letter);
  }

  public void printPiecesWhiteTop(int col){
    String piece = "";
    if(col == 1) {
      piece=WHITE_ROOK;
    } else if(col == 2){
      piece = WHITE_KNIGHT;
    } else if (col == 3) {
      piece = WHITE_BISHOP;
    } else if (col == 4) {
      piece = WHITE_KING;
    } else if (col == 5) {
      piece = WHITE_QUEEN;
    } else if (col == 6) {
      piece = WHITE_BISHOP;
    } else if (col == 7){
      piece = WHITE_KNIGHT;
    } else if (col == 8) {
      piece = WHITE_ROOK;
    }
    System.out.print(piece);
  }

  public void printPiecesBlackBottom(int col){
    String piece = "";
    if(col == 1) {
      piece=BLACK_ROOK;
    } else if(col == 2){
      piece = BLACK_KNIGHT;
    } else if (col == 3) {
      piece = BLACK_BISHOP;
    } else if (col == 4) {
      piece = BLACK_KING;
    } else if (col == 5) {
      piece = BLACK_QUEEN;
    } else if (col == 6) {
      piece = BLACK_BISHOP;
    } else if (col == 7){
      piece = BLACK_KNIGHT;
    } else if (col == 8) {
      piece = BLACK_ROOK;
    }
    System.out.print(piece);
  }

}
