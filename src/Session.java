import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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

    private User user;

    /**
     * Constructeur de la classe Session
     * 
     * @param socket La socket de la session
     * 
     * @param server Le serveur de la session
     */
    public Session(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.user = null;
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

    public void sendObject(Object object) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
        out.writeObject(object);
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

    /**
     * Gère la commande /connect
     * 
     * @param message Le message à traiter
     */
    private void handleConnectCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            String username = args[1];
            if (!this.server.getUsers().contains(new User(username))) {
                User newUser = new User(username);
                this.server.getUsers().add(newUser);
                this.user = newUser;
                System.out.println("Client " + this.socket.getInetAddress().getHostAddress() + " connected.");
            } else {
                System.out.println(this.server.getUsers());
                for (User u : this.server.getUsers()) {
                    if (u.getName().equals(username)) {
                        this.user = u;
                    }
                }
            }
        }
    }

    /**
     * Gère la commande /follow
     * 
     * @param message Le message à traiter
     */
    private void handleFollowCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            String username = args[1];
            for (User u : this.server.getUsers()) {
                if (u.getName().equals(username)) {
                    user.addAbonnement(u);
                }
            }
        }
    }

    /**
     * Gère la commande /unfollow
     * 
     * @param message Le message à traiter
     */
    private void handleUnfollowCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            String username = args[1];
            for (User u : this.user.getAbonnements()) {
                if (u.getName().equals(username)) {
                    user.removeAbonnement(u);
                }
            }
        }
    }

    /**
     * Gère la commande /post
     * 
     * @param message Le message à traiter
     */
    private void handlePostCommand(String message) {
        String[] args = message.split(" ", 2);
        String post = String.join(" ", args[1]);
        Message newMessage = new Message(post, user);
        this.user.addMessage(newMessage);
    }

    /**
     * Gère la commande /like
     * 
     * @param message Le message à traiter
     */
    private void handleLikeCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            int id = Integer.parseInt(args[1]);
            for (User u : this.server.getUsers()) {
                for (Message m : u.getMessages()) {
                    if (m.getId() == id) {
                        m.addLiker(user);
                    }
                }
            }
        }
    }

    /**
     * Gère la commande /unlike
     * 
     * @param message Le message à traiter
     */
    private void handleUnlikeCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            int id = Integer.parseInt(args[1]);
            for (User u : this.server.getUsers()) {
                for (Message m : u.getMessages()) {
                    if (m.getId() == id) {
                        m.removeLiker(user);
                    }
                }
            }
        }
    }

    /**
     * Gère la commande /delete
     * 
     * @param message Le message à traiter
     */
    private void handleDeleteCommand(String message) {
        String[] args = message.split(" ");
        if (args.length == 2) {
            int id = Integer.parseInt(args[1]);
            this.user.getMessages().remove(this.user.getMessage(id));
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = this.receive(); // On attend de recevoir un message
                System.out.println("Message de " + this.socket.getInetAddress().getHostAddress() + ", port "
                        + this.socket.getPort() + " : " + message); // On affiche le message reçu
                if (message.equals("exit")) { // Si le message est "exit", on quitte la boucle
                    System.out.println("Client " + this.socket.getInetAddress().getHostAddress() + " disconnected.");
                    break;
                } else if (message.startsWith("/connect")) {
                    handleConnectCommand(message);
                } else if (user != null) {
                    if (message.startsWith("/follow")) {
                        handleFollowCommand(message);
                    } else if (message.startsWith("/unfollow")) {
                        handleUnfollowCommand(message);
                    } else if (message.startsWith("/post")) {
                        handlePostCommand(message);
                    } else if (message.startsWith("/like")) {
                        handleLikeCommand(message);
                    } else if (message.startsWith("/unlike")) {
                        handleUnlikeCommand(message);
                    } else if (message.startsWith("/delete")) {
                        handleDeleteCommand(message);
                    }
                }
                this.sendObject(this.user);
            }
            this.close(); // On ferme la socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}