import bd.ConnexionSqlite;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;
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

    private ConnexionSqlite connexionSqlite;

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
        try {
            this.connexionSqlite = new ConnexionSqlite();
            this.connexionSqlite.connecter();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void deleteMessage(int id_message){
        try{
            Statement statement = this.connexionSqlite.createStatement();
            statement.executeUpdate("DELETE FROM MESSAGE WHERE id_message = " + id_message);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUtilisateur(int id_utilisateur){
        try{
            Statement statement = this.connexionSqlite.createStatement();
            statement.executeUpdate("DELETE FROM USER WHERE idUser = " + id_utilisateur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(Message message){
        try{
            Statement statement = this.connexionSqlite.createStatement();
            statement.executeUpdate("INSERT INTO MESSAGE (idMessage, nameUser, content, date, like) VALUES " +
                    "(" + message.getIdMessage() + ", " + message.getNomUtilisateur() + ", " + message.getContenu() + ", " + message.getDate() + ", " + message.getLikes() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message jsonToMessage(String json){
        // le json des de cette forme :
        //message:
        //"id" : 987697
        //"user" : "toto",
        //"content" : "Hello world !",
        //"date" : "2024-01-20T09:00:00Z’,
        //"likes" : 3

        String[] jsonSplit = json.split(",");
        int id = Integer.parseInt(jsonSplit[0].split(":")[1]);
        String nameUser = jsonSplit[1].split(":")[1];
        String content = jsonSplit[2].split(":")[1];
        String date = jsonSplit[3].split(":")[1];
        int likes = Integer.parseInt(jsonSplit[4].split(":")[1]);
        return new Message(id, nameUser, content, date, likes);
    }


}