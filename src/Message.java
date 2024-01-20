public class Message {
    private int idMessage;
    private String nomUtilisateur;
    private String contenu;
    private String date;
    private int likes;

    public Message(int idMessage, String nomUtilisateur, String contenu, String date, int likes) {
        this.idMessage = idMessage;
        this.nomUtilisateur = nomUtilisateur;
        this.contenu = contenu;
        this.date = date;
        this.likes = likes;
    }

    public int getIdMessage() {
        return this.idMessage;
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    public String getContenu() {
        return this.contenu;
    }

    public String getDate() {
        return this.date;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String toString() {
        return "Message{idMessage=" + this.idMessage + ", nomUtilisateur=" + this.nomUtilisateur + ", contenu=" + this.contenu + ", date=" + this.date + ", likes=" + this.likes + "}";
    }



}
