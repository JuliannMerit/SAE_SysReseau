import java.util.Scanner;
import java.io.IOException;

public class ThreadServer extends Thread {
    private Server server;

    public ThreadServer(Server server) {
        this.server = server;
    }

    public void run() {
        try {
            Scanner commande = new Scanner(System.in);
            while (true) {
                String input = commande.nextLine();
                if (input.equals("exit")) {
                    this.server.close();
                    break;
                } else if (input.startsWith("/delete")) {
                    int id = Integer.parseInt(input.split(" ")[1]);
                    for (User user : this.server.getUsers()) {
                        user.removeMessage(user.getMessage(id));
                    }
                } else if (input.startsWith("/remove")) {
                    String username = input.split(" ")[1];
                    this.server.removeUser(username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}