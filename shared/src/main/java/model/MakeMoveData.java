package model;

import chess.ChessGame;
import chess.ChessMove;

public record MakeMoveData(int gameID, ChessMove move, ChessGame.TeamColor color) {
}
