import ui.Prelogin;

public class Main {
    public static void main(String[] args) throws Exception {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        System.out.println("Welcome to 240 chess. Type 'help' to get started...");

        new Prelogin(serverUrl).run();
    }
}