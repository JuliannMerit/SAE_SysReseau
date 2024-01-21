import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class User implements Serializable {

    private String name;

    private Set<User> abonnements;

    private List<Message> messages;

    public User(String name) {
        this.name = name;
        this.abonnements = new HashSet<>();
        this.messages = new ArrayList<>();
    }

    /**
     * Retourne le nom de l'utilisateur
     * 
     * @return Le nom de l'utilisateur
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retourne les abonnements de l'utilisateur
     * 
     * @return Les abonnements de l'utilisateur
     */
    public Set<User> getAbonnements() {
        return this.abonnements;
    }

    /**
     * Ajoute un abonnement à l'utilisateur
     * 
     * @param user L'utilisateur à ajouter
     */
    public void addAbonnement(User user) {
        this.abonnements.add(user);
    }

    /**
     * Retire un abonnement à l'utilisateur
     * 
     * @param user L'utilisateur à retirer
     */
    public void removeAbonnement(User user) {
        this.abonnements.remove(user);
    }

    /**
     * Retourne les messages de l'utilisateur
     * 
     * @return Les messages de l'utilisateur
     */
    public List<Message> getMessages() {
        return this.messages;
    }

    /**
     * Ajoute un message à l'utilisateur
     * 
     * @param message Le message à ajouter
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Retire un message à l'utilisateur
     * 
     * @param message Le message à retirer
     */
    public void removeMessage(Message message) {
        this.messages.remove(message);
    }

    /**
     * Retourne le message avec l'id donné
     * 
     * @param id L'id du message à retourner
     * @return Le message avec l'id donné
     */
    public Message getMessage(int id) {
        for (Message message : this.messages) {
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    /**
     * Retourne vrai si l'objet est un utilisateur et a le même nom que
     * l'utilisateur
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.name.equals(user.name);
        }
        return false;
    }

    /**
     * Retourne un affichage de l'utilisateur
     */
    @Override
    public String toString() {
        return this.name;
    }
}
