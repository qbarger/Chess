package server;

import dataAccess.*;
import service.ClearHandler;
import service.ClearService;
import service.RegisterHandler;
import service.RegisterService;
import spark.*;

public class Server {

    public static void main (String[] args) {
        new Server().run(8080);
    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDao userDB = new MemoryUserDao();
        AuthDao authDB = new MemoryAuthDao();
        GameDao gameDB = new MemoryGameDao();
        ClearService clearService = new ClearService(userDB, authDB, gameDB);
        RegisterService registerService = new RegisterService(userDB, authDB);

        Spark.delete("/db", (req, res) -> (new ClearHandler(clearService.userDB, clearService.authDB, clearService.gameDB)).clear(req, res));
        Spark.post("/user", (req, res) -> (new RegisterHandler(registerService.userDB, registerService.authDB).register(req, res)));
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
