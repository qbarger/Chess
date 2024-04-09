package server;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.memoryDAOs.MemoryAuthDao;
import dataAccess.memoryDAOs.MemoryGameDao;
import dataAccess.memoryDAOs.MemoryUserDao;
import model.ErrorData;
import server.websocket.WebSocketHandler;
import service.*;
import service.handler.*;
import spark.*;

public class Server {

    public static void main (String[] args) throws DataAccessException {
        new Server().run(8080);
    }


    public int run(int desiredPort) throws DataAccessException {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDao userDB = new DatabaseUserDao();
        AuthDao authDB = new DatabaseAuthDao();
        GameDao gameDB = new DatabaseGameDao();
        ClearService clearService = new ClearService(userDB, authDB, gameDB);
        RegisterService registerService = new RegisterService(userDB, authDB);
        LoginService loginService = new LoginService(userDB,authDB);
        LogoutService logoutService = new LogoutService(userDB,authDB);
        CreateGameService createGameService = new CreateGameService(authDB, gameDB);
        ListGamesService listGamesService = new ListGamesService(authDB, gameDB);
        UpdateGameService updateGameService = new UpdateGameService(authDB, gameDB);
        WebSocketHandler webSocketHandler = new WebSocketHandler();

        Spark.webSocket("/connect", webSocketHandler);

        Spark.delete("/db", (req, res) -> (new ClearHandler(clearService.userDB, clearService.authDB, clearService.gameDB)).clear(req, res));
        Spark.post("/user", (req, res) -> (new RegisterHandler(registerService.userDB, registerService.authDB).register(req, res)));
        Spark.post("/session", (req, res) -> (new LoginHandler(loginService.userDB, loginService.authDB).login(req, res)));
        Spark.delete("/session", (req, res) -> (new LogoutHandler(logoutService.userDB, logoutService.authDB).logout(req, res)));
        Spark.post("/game", (req, res) -> (new CreateGameHandler(createGameService.gameDB, createGameService.authDB).createGame(req, res)));
        Spark.get("/game", (req, res) -> (new ListGamesHandler(listGamesService.authDB, listGamesService.gameDB).listGames(req, res)));
        Spark.put("/game", (req, res) -> (new UpdateGameHandler(updateGameService.authDB, updateGameService.gameDB).joinGame(req, res)));
        Spark.exception(DataAccessException.class, this::exceptionHandler);
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(DataAccessException exception, Request req, Response res){
        res.status(exception.getStatusCode());
        res.body(new Gson().toJson(new ErrorData(exception.getMessage())));
    }
}
