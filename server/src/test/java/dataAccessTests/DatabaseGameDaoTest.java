package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.GameList;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseGameDaoTest {

  private DatabaseGameDao databaseGameDao;

  @BeforeEach
  void setup() throws DataAccessException {
    databaseGameDao = new DatabaseGameDao();
  }


  @Test
  void createGame() throws DataAccessException, SQLException {
    databaseGameDao.clear();
    GameData game = new GameData(1, null, null, "game1", new ChessGame());
    databaseGameDao.createGame(game);
    GameData newGame = databaseGameDao.getGame(1);
    assertEquals(game, newGame);
  }

  @Test
  void createGameFails() throws DataAccessException{
    try {
      databaseGameDao.clear();
      GameData game=new GameData(1, null, null, "game1", new ChessGame());
      databaseGameDao.createGame(game);
      GameData newGame=databaseGameDao.getGame(2);
      fail("Expected Data access exception.");
    } catch (DataAccessException | SQLException exception){
      assertEquals("Error: Unable to read data: Error: Game not found.", exception.getMessage());
    }
  }

  @Test
  void getGame() throws DataAccessException, SQLException {
    databaseGameDao.clear();
    GameData game = new GameData(1, null, null, "game1", new ChessGame());
    databaseGameDao.createGame(game);
    GameData newGame = databaseGameDao.getGame(1);
    assertNotNull(newGame);
  }

  @Test
  void getGameFails() {
    try {
      databaseGameDao.clear();
      GameData game=new GameData(1, null, null, "game1", new ChessGame());
      databaseGameDao.createGame(game);
      GameData newGame=databaseGameDao.getGame(2);
      fail("Expected Data access exception.");
    } catch (DataAccessException | SQLException exception){
      assertEquals("Error: Unable to read data: Error: Game not found.", exception.getMessage());
    }
  }

  @Test
  void joinGame() throws DataAccessException, SQLException {
    databaseGameDao.clear();
    GameData game = new GameData(1, null, null, "game1", new ChessGame());
    databaseGameDao.createGame(game);
    GameData newGame = new GameData(game.gameID(), "john", game.blackUsername(), game.gameName(), game.game());
    databaseGameDao.joinGame(newGame);
    assertEquals(newGame, databaseGameDao.getGame(newGame.gameID()));
  }

  @Test
  void joinGameFails() throws DataAccessException, SQLException {
      databaseGameDao.clear();
      GameData game=new GameData(1, null, null, "game1", new ChessGame());
      databaseGameDao.createGame(game);
      GameData newGame=new GameData(2, "john", game.blackUsername(), game.gameName(), game.game());
      databaseGameDao.joinGame(newGame);
      GameData check = databaseGameDao.getGame(1);
      assertNotEquals(newGame, check);
  }

  @Test
  void listGames() throws DataAccessException{
    databaseGameDao.clear();
    GameData game1 = new GameData(1, null, null, "game1", new ChessGame());
    GameData game2 = new GameData(2, null, null, "game2", new ChessGame());
    GameData game3 = new GameData(3, null, null, "game3", new ChessGame());
    databaseGameDao.createGame(game1);
    databaseGameDao.createGame(game2);
    databaseGameDao.createGame(game3);
    GameList newGame = databaseGameDao.listGames();
    assertNotNull(newGame);
  }

  @Test
  void listGamesFails() throws DataAccessException{
    databaseGameDao.clear();
    GameData game1 = new GameData(1, null, null, "game1", new ChessGame());
    GameData game2 = new GameData(2, null, null, "game2", new ChessGame());
    GameData game3 = new GameData(3, null, null, "game3", new ChessGame());
    databaseGameDao.createGame(game1);
    databaseGameDao.createGame(game2);
    databaseGameDao.createGame(game3);
    GameList list = databaseGameDao.listGames();
    assertNotEquals(2, list.games().size());
  }
}