package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by Dell on 21/09/2016.
 */
public class DBUtil {
    private static final Logger logger = Logger.getLogger (DBUtil.class.getName ());
    private static Connection connect;
    private static String url = "jdbc:h2:~/gestadvent";
    private static String user = "root";
    private static String pass = "";

    public static Connection getConnexion() throws SQLException, ClassNotFoundException {
        if (connect == null) {
            try {
                Class.forName ("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace ();
            }
            connect = DriverManager.getConnection (url, user, pass);
        }
        return connect;
    }

    public static void closeConnection() {
        if (connect != null)
            try {
                connect.close ();
            } catch (SQLException e) {
                e.printStackTrace ();
            }
    }

    public static void createSchema(Connection con) throws ClassNotFoundException, SQLException {
        logger.info ("Creation des tables");
        Statement st = con.createStatement ();
        String tableMembre = "CREATE TABLE membre(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,nom VARCHAR(255) NOT NULL,prenom VARCHAR(255) NOT NULL,"
                + "date_N DATE NOT NULL,lieu_N DATE NOT NULL,phone VARCHAR(255) NOT NULL,email VARCHAR(255) NOT NULL,photo VARCHAR(255) NOT NULL ," +
                "sexe VARCHAR(255) NOT NULL,adresse VARCHAR(255) NOT NULL,profession VARCHAR(255) NOT NULL,extras TEXT,status VARCHAR(255),situation_M VARCHAR(255) NOT NULL)";

        String tableBapteme = "CREATE TABLE bapteme(id INT AUTO_INCREMENT PRIMARY KEY,lieu_b VARCHAR(255) NOT NULL,date_b DATE NOT NULL," +
                "pasteur VARCHAR(255) NOT NULL,eglise_d VARCHAR(255),eglise_pro VARCHAR(255),date_transfert DATE," +
                "CONSTRAINT fk_membre_id FOREIGN KEY (membre) REFERENCES Membre(id) ON DELETE CASCADE )";

        String tableUser = "CREATE TABLE user(username VARCHAR(255) NOT NULL,password VARCHAR(255))";
        st.executeUpdate (tableMembre);
        st.executeUpdate (tableBapteme);
        st.executeUpdate (tableUser);
    }

    public static boolean schemaExists(Connection con) {
        logger.info ("Verification de l'existence du schema");

        try {
            Statement st = con.createStatement ();
            st.executeQuery ("SELECT count(*) FROM membre JOIN bapteme ON membre.id = bapteme.membre");
            logger.info ("Schema exists");
        } catch (SQLException e) {
            logger.info ("Creation d'une nouvelle BD en raison de l'inexistence");
            return false;
        }

        return true;
    }
}
