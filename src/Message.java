import java.sql.Date;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public class Message implements Serializable {
    private String content;

    private User author;

    private Date date;

    private static int lastId = 0;

    private int id;

    private Set<User> likers;

    public Message(String content, User author) {
        this.content = content;
        this.author = author;
        this.date = new Date(System.currentTimeMillis());
        this.id = lastId;
        lastId += 1;
        this.likers = new HashSet<>();

    }

    /**
     * Retourne le contenu du message
     * 
     * @return Le contenu du message
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Retourne l'auteur du message
     * 
     * @return L'auteur du message
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Retourne la date du message
     * 
     * @return La date du message
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Retourne l'id du message
     * 
     * @return L'id du message
     */
    public int getId() {
        return this.id;
    }

    /**
     * Renvoie un affichage du message
     */
    @Override
    public String toString() {
        return this.author + " : " + this.content + " (" + this.date + ")";
    }

    /**
     * Retourne vrai si l'objet est un message et a le même contenu, auteur et date
     * que le message
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message message = (Message) obj;
            return this.content.equals(message.content) && this.author.equals(message.author)
                    && this.date.equals(message.date);
        }
        return false;
    }

    /**
     * Retourne la liste des utilisateurs qui ont liké le message
     * 
     * @param user L'utilisateur à ajouter à la liste
     */
    public void addLiker(User user) {
        this.likers.add(user);
    }

    /**
     * Retire un utilisateur de la liste des utilisateurs qui ont liké le message
     * 
     * @param user L'utilisateur à retirer de la liste
     */
    public void removeLiker(User user) {
        this.likers.remove(user);
    }

    /**
     * Retourne la liste des utilisateurs qui ont liké le message
     * 
     * @return La liste des utilisateurs qui ont liké le message
     */
    public Set<User> getLikers() {
        return this.likers;
    }

    /**
     * Retourne le nombre de likes du message
     * 
     * @return Le nombre de likes du message
     */
    public int getNbLikes() {
        return this.likers.size();
    }
}
