import chess.*;
import ui.ServerFacade;

public class Main {
    public static void main(String[] args) throws Exception {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new ServerFacade(serverUrl).run();
    }
}