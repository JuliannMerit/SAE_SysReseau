package bd;

import java.sql.*;

public class ConnexionSqlite {

        private Connection sqlite=null;
        private boolean connecte=false;

        public ConnexionSqlite() throws ClassNotFoundException{
            this.sqlite = null;
            this.connecte = false;
            Class.forName("org.sqlite.JDBC");
        }

        public void connecter() throws SQLException {
            // si tout c'est bien passé la connexion n'est plus nulle
            this.connecte=this.sqlite!=null;
            this.sqlite = null;
            this.connecte = false;
            this.sqlite = DriverManager.getConnection("jdbc:sqlite:db/reseaux.sqlite3");
            this.connecte = true;
            System.out.println("Connexion réussie");
        }
        public void close() throws SQLException {
            // fermer la connexion
            this.sqlite.close();
            this.connecte=false;
        }

        public boolean isConnecte(){
            return this.connecte;
        }

        public Statement createStatement() throws SQLException {
            return this.sqlite.createStatement();
        }

        public PreparedStatement prepareStatement(String requete) throws SQLException{
            return this.sqlite.prepareStatement(requete);
        }
        public Connection getSqlite() {
            return sqlite;
        }
}
