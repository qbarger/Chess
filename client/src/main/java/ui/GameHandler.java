package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
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

  public void printMessage(NotificationMessage message){
    System.out.println();
    System.out.println();
    System.out.println(message.getMessage());
    System.out.print("[LOGGED_IN] >>> ");
  }

  public void printBoard(ChessGame game, ChessGame.TeamColor color){
    if(color == ChessGame.TeamColor.WHITE || color == null){
      printWhite(game);
    } else {
      printBlack(game);
    }
    System.out.println();
    System.out.print("[LOGGED_IN] >>> ");
  }

  public void printWhite(ChessGame game){
    System.out.println();
    System.out.println();
    ChessBoard board = game.getBoard();
    int size = 10;
    String space;
    for(int row = 0; row < size; row++){
      for(int col = 0; col < size; col++){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
          System.out.print(space);
        } else {
          space = SET_BG_COLOR_LIGHT_GREY;
          System.out.print(space);
        }
        if(!(row > 8 || row < 1 || col > 8 || col < 1)) {
          ChessPiece chessPiece=board.getPiece(new ChessPosition(row, col));
          if (!(chessPiece == null)) {
            printPiece(chessPiece);
          } else {
            String empty=EMPTY;
            System.out.print(empty);
          }
        } else {
          if(row > 8){
            printLettersBottom(col);
          } else if (row < 1) {
            printLettersTop(col);
          } else if(col > 8 || col < 1){
            printNumbers(row);
          }
        }
      }
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void printBlack(ChessGame game){
    System.out.println();
    System.out.println();
    ChessBoard board = game.getBoard();
    int size = 9;
    String space;
    for(int row = size; row >= 0; row--){
      for(int col = size; col >= 0; col--){
        if(row % 2 == col % 2){
          space = SET_BG_COLOR_WHITE;
          System.out.print(space);
        } else {
          space = SET_BG_COLOR_LIGHT_GREY;
          System.out.print(space);
        }
        if(!(row > 8 || row < 1 || col > 8 || col < 1)) {
          ChessPiece chessPiece=board.getPiece(new ChessPosition(row, col));
          if (!(chessPiece == null)) {
            printPiece(chessPiece);
          } else {
            String empty=EMPTY;
            System.out.print(empty);
          }
        } else {
          if(row > 8){
            printLettersBottom(col);
          } else if (row < 1) {
            printLettersTop(col);
          } else if(col > 8 || col < 1){
            printNumbers(row);
          }
        }
      }
      System.out.println("\u001B[0m ");
    }
    System.out.println();
  }

  public void printPiece(ChessPiece chessPiece){
    if(chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
        System.out.print(WHITE_PAWN);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
        System.out.print(WHITE_BISHOP);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
        System.out.print(WHITE_KNIGHT);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
        System.out.print(WHITE_ROOK);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
        System.out.print(WHITE_QUEEN);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
        System.out.print(WHITE_KING);
      }
    } else {
      if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
        System.out.print(BLACK_PAWN);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
        System.out.print(BLACK_BISHOP);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
        System.out.print(BLACK_KNIGHT);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
        System.out.print(BLACK_ROOK);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
        System.out.print(BLACK_QUEEN);
      } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
        System.out.print(BLACK_KING);
      }
    }
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
}
