import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe Session
 */
public class Session extends Thread {
    /**
     * Socket de la session
     */
    private Socket socket;

    private Server server;

    /**
     * Constructeur de la classe Session
     * 
     * @param socket La socket de la session
     */
    public Session(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Ferme la socket de la session
     * 
     * @throws IOException Si la socket ne peut pas être fermée
     */
    public void close() throws IOException {
        this.socket.close();
    }

    /**
     * Envoie un message au client de la session (par la socket)
     * 
     * @param message Le message à envoyer
     * @throws IOException Si le message ne peut pas être envoyé
     */
    public void send(String message) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(message);
    }

    public void disconnectUser() throws IOException {
        this.server.removeSession(this);
    }

    /**
     * Reçoit un message du client de la session (par la socket)
     * 
     * @return Le message reçu
     * @throws IOException Si le message ne peut pas être reçu
     */
    public String receive() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = this.receive(); // On attend de recevoir un message
                String[] commande = message.split(" ");
                System.out.println("Message de " + this.socket.getInetAddress().getHostAddress() + ", port " + this.socket.getPort() + " : " + message); // On affiche le message reçu
                if (commande[0].equals("exit")) { // Si le message est "exit", on quitte la boucle
                    System.out.println("Client " + this.socket.getInetAddress().getHostAddress() + " disconnected.");
                    this.disconnectUser();
                    System.out.println(this.server.getSessions());
                    break;
                }
                this.send("reçu"); // On envoie "reçu" au client
            }
            this.close(); // On ferme la socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}