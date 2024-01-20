import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe Client
 */
public class Client {
    /**
     * Socket du client
     */
    private Socket socket;

    private List<Client> abonnes;

    private List<Client> abonnements;

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
        this.abonnes = new ArrayList<>();
        this.abonnements = new ArrayList<>();
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
     * Ajoute un abonnement au client
     * @param client Le client à ajouter
     */
    public void addAbonnement(Client client) {
        this.abonnements.add(client);
    }

    /**
     * Retire un abonnement du client
     * @param client Le client à retirer
     */
    public void removeAbonnement(Client client) {
        this.abonnements.remove(client);
    }

    /**
     * Retourne la liste des abonnements du client
     * @return La liste des abonnements du client
     */
    public List<Client> getAbonnements() {
        return this.abonnements;
    }

    /**
     * Ajoute un abonné au client
     * @param client Le client à ajouter
     */
    public void addAbonne(Client client) {
        this.abonnes.add(client);
    }

    /**
     * Retire un abonné du client
     * @param client Le client à retirer
     */
    public void removeAbonne(Client client) {
        this.abonnes.remove(client);
    }

    /**
     * Retourne la liste des abonnés du client
     * @return La liste des abonnés du client
     */
    public List<Client> getAbonnes() {
        return this.abonnes;
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
    public String receive() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 8081);
            Scanner scan = new Scanner(System.in);

            while (true) {
                String message = scan.nextLine();
                client.send(message);
                if (message.equals("exit")) {
                    break;
                }
                System.out.println("Server: " + client.receive());
            }
            client.close();
            scan.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}