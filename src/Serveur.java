import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur{
    public static void main(String[] args) {
        try {
            ServerSocket socketServer = new ServerSocket(4444);
            Socket socketClient = socketServer.accept();
            System.out.println("connexion d’un client");
            while (true) {
                // lire un message envoyé par le client
                int message = socketClient.getInputStream().read();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
