import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe Server
 */
public class Server {
    /**
     * Socket du serveur
     */
    private ServerSocket serverSocket;

    private List<Session> sessions;

    /**
     * Constructeur de la classe Server
     * 
     * @param port Le port du serveur
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        this.sessions = new ArrayList<>();
    }

    /**
     * Ferme la socket du serveur
     * 
     * @throws IOException Si la socket ne peut pas être fermée
     */
    public void close() throws IOException {
        this.serverSocket.close();
    }

    /**
     * Accepte une nouvelle connexion
     * 
     * @throws IOException Si la connexion ne peut pas être acceptée
     */
    public void accept() throws IOException {
        Socket socket = this.serverSocket.accept();
        System.out.println("New client connected : " + socket.getInetAddress().getHostAddress() + " on port " + socket.getPort() + ".");
        this.addSession(new Session(socket, this));
        Session session = new Session(socket, this);
        session.start();
    }

    /**
     * Boucle infinie d'acceptation de connexions
     * 
     * @throws IOException Si la connexion ne peut pas être acceptée
     */
    public void run() throws IOException {
        while (true) {
            this.accept();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(8081);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public void removeSession(Session session) {
        this.sessions.remove(session);
    }

    public List<Session> getSessions() {
        return this.sessions;
    }
}