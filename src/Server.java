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

    private List<User> users;

    /**
     * Constructeur de la classe Server
     * 
     * @param port Le port du serveur
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        this.users = new ArrayList<>();
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
        System.out.println("New client connected : " + socket.getInetAddress().getHostAddress() + " on port "
                + socket.getPort() + ".");
        Session session = new Session(socket, this);
        session.start();
    }

    /**
     * Boucle infinie d'acceptation de connexions (seul fonction réel du serveur
     * pour le moment)
     * 
     * @throws IOException Si la connexion ne peut pas être acceptée
     */
    public void run() throws IOException {
        new ThreadServer(this).start();
        while (true) {
            this.accept();
        }
    }

    /**
     * Retourne la liste des utilisateurs connectés
     * 
     * @return La liste des utilisateurs connectés
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Retourne l'utilisateur avec le nom donné
     * 
     * @param username Le nom de l'utilisateur à retourner
     * @return L'utilisateur avec le nom donné
     */
    public User getUser(String username) {
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Supprime l'utilisateur avec le nom donné, ainsi ses likes, et listes
     * d'abonnements dont il fait partie
     * 
     * @param username Le nom de l'utilisateur à supprimer
     */
    public void removeUser(String username) {
        User userToRemove = null;

        for (User user : this.users) {
            if (user.getName().equals(username)) {
                userToRemove = user;
            }
        }
        for (User user : this.users) {
            if (user.getAbonnements().contains(userToRemove)) {
                user.removeAbonnement(userToRemove);
            }
            for (Message message : user.getMessages()) {
                if (message.getLikers().contains(userToRemove)) {
                    message.removeLiker(userToRemove);
                }
            }
        }

        this.users.remove(userToRemove);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(8081);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}