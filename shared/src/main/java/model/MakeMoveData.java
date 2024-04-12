package model;

import chess.ChessMove;

public record MakeMoveData(int gameID, ChessMove move) {
}
