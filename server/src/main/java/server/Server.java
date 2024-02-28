package server;

import service.ClearHandler;
import service.ClearService;
import service.RegisterHandler;
import spark.*;

public class Server {

    public static void main (String[] args) {
        new Server().run(8080);
    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", (req, res) -> (new ClearHandler();
        //Spark.post("/user", RegisterHandler::register);
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
