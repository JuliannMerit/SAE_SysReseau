import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Classe Client
 */
public class Client {
    /**
     * Socket du client
     */
    private Socket socket;

    private User user;

    /**
     * Constructeur de la classe Client
     * 
     * @param host Le nom ou l'ip du serveur
     * @param port Le port du serveur
     * @throws UnknownHostException Si le serveur n'est pas trouvé
     * @throws IOException
     */
    public Client(String host, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(host, port);
        System.out.println("Connected to server on port " + port);
        this.user = null;
    }

    /**
     * Ferme la socket du client
     * 
     * @throws IOException Si la socket ne peut pas être fermée
     */
    public void close() throws IOException {
        this.socket.close();
    }

    /**
     * Envoie un message au serveur
     * 
     * @param message Le message à envoyer
     * @throws IOException Si le message ne peut pas être envoyé
     */
    public void send(String message) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(message);
    }

    /**
     * Reçoit un message du serveur
     * 
     * @return Le message reçu
     * @throws IOException Si le message ne peut pas être reçu
     */
    public User receive() {
        try {
            return (User) new ObjectInputStream(this.socket.getInputStream()).readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Affiche les messages des abonnements du user connecté
     */
    public void affichage() {
        for (User followed : this.user.getAbonnements()) {
            for (Message message : followed.getMessages()) {
                System.out.println(followed.getName() + " : " + message.getContent() + " ( date : " + message.getDate()
                        + ")" + " (" + message.getId() + ")" + " (" + message.getNbLikes() + " likes )");
            }
        }
    }

    /**
     * Boucle infinie d'envoi de messages au serveur (seul fonction réel du client
     * pour le moment)
     * 
     * @throws IOException Si le message ne peut pas être envoyé
     */
    public void run() throws IOException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String message = scan.nextLine();
            this.send(message);
            if (message.equals("exit")) {
                break;
            }
            this.user = this.receive();
            if (this.user != null) {
                System.out.println("Abonnements :" + this.user.getAbonnements());
                System.out.println("Messages :");
                this.affichage();
            }
        }
        scan.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 8081);
            client.run();
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}