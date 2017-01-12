package model;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.*;

/**
 * Created by Dell on 21/09/2016.
 */
public class MembreBDAO extends DAO<Membre, Bapteme> {

    private static final String AJOUTERMEMBRE = "INSERT INTO membre (nom,prenom,date_N,lieu_N" +
            ",phone,email,photo,sexe,adresse,profession,extras,situation_M) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String AJOUTERBAPTEME = "INSERT INTO bapteme(lieu_b,date_b,pasteur,eglise_d,eglise_pro,date_transfert,membre) VALUES (?,?,?,?,?,?,?)";

    private static final String MODIFIERMEMBRE = "UPDATE membre SET nom = ?,prenom = ?,date_N = ?,lieu_N = ?,phone = ?," +
            "email = ?,photo = ?,adresse = ?,profession = ?,extras = ? WHERE id = ? ";

    private static final String MODIFIERBAPTEME = "UPDATE bapteme SET lieu_b = ?,date_b = ?,pasteur = ?,eglise_d = ?" +
            ",eglise_pro = ?,date_transfert = ? WHERE id =?";

    private static final String INFOSMEMBRE = "SELECT id,nom,prenom,sexe,date_N,situation_M,adresse,profession," +
            "phone,email FROM membre WHERE status = ? ";


    private static final String INFOSMEMBREWITHCRITERIA = "SELECT id, nom,prenom,sexe,date_N,situation_M,adresse," +
            "profession,phone,email FROM membre WHERE status = ?";

    private static final String MEMBREDETAILS = "SELECT * FROM membre JOIN bapteme ON membre.id = bapteme.membre WHERE membre.id =? ";

    private static final String DELETEMEMBRE = "DELETE FROM membre WHERE id = ?";

    private static final String CHANGESTATUS = "UPDATE membre SET status = ? WHERE id = ?";
    private ObservableList<Membre> membreList;

    public MembreBDAO(Connection con) throws SQLException, ClassNotFoundException {
        super (con);
        DAO.con = DBUtil.getConnexion ();// TODO Auto-generated constructor stub
        if (!DBUtil.schemaExists (con)) {
            try {
                DBUtil.createSchema (con);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace ();
            }
        }

    }

    @Override
    public Object[] getUnique(Membre membre, Bapteme bapteme) {
        try {
            PreparedStatement ps = con.prepareStatement (MEMBREDETAILS);
            ps.setInt (1, membre.getId ());
           /* ps.setString(2, membre.getNom());
            ps.setString(3, membre.getPrenom());*/

            ResultSet rs = ps.executeQuery ();
            while (rs.next ()) {
                membre.setId (rs.getInt ("id"));
                membre.setNom (rs.getString ("nom"));
                membre.setPrenom (rs.getString ("prenom"));
                membre.setDateNaissance (rs.getDate ("date_N").toLocalDate ());
                membre.setLieuNaissance (rs.getString ("lieu_N"));
                membre.setTelephone (rs.getString ("phone"));
                membre.setEmail (rs.getString ("email"));
                membre.setPhoto (rs.getBinaryStream ("photo"));
                membre.setSexe (rs.getString ("sexe"));
                membre.setAdresse (rs.getString ("adresse"));
                membre.setProfession (rs.getString ("profession"));
                membre.setExtra (rs.getString ("extras"));
                membre.setSituationM (rs.getString ("situation_M"));

              /*  membre = new Membre(rs.getString("nom"), rs.getString("prenom"), rs.getString("phone"),
                        rs.getString("sexe"), rs.getString("profession"), rs.getString("email"),
                        rs.getString("adresse"), rs.getBinaryStream("photo"), rs.getDate("date_N").toLocalDate(),
                        rs.getString("lieu_N"), rs.getString("extras"), rs.getString("situation_M"));*/

                bapteme = new Bapteme (rs.getDate ("date_b").toLocalDate (), rs.getString ("lieu_b"), rs.getString ("pasteur"),
                        rs.getString ("eglise_d"), rs.getString ("eglise_pro"), rs.getDate ("date_transfert").toLocalDate ());


         /*       bapteme.setPasteur(rs.getString("pasteur"));
                bapteme.setLieuBapteme(rs.getString("lieu_b"));
                bapteme.setEgliseDest(rs.getString("eglise_d"));
                bapteme.setEglisePro(rs.getString("eglise_pro"));
                bapteme.setDateBapteme(rs.getDate("date_b").toLocalDate());
                bapteme.setDateTransfert(rs.getDate("date_transfert").toLocalDate());*/
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return new Object[]{membre, bapteme};
    }


    @Override
    public int update(Membre membre, Bapteme bapteme) {
        String query = "SELECT id FROM membre WHERE nom = " + "'" + membre.getNom () + "'" + " AND prenom = " + "'" + membre.getPrenom () + "'";
        int r = 0, r1 = 0;
        try {
           /* if (!DBUtil.schemaExists(con)) {
                try {
                    DBUtil.createSchema(con);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }*/

            PreparedStatement ps = con.prepareStatement (MODIFIERMEMBRE);
            ps.setString (1, membre.getNom ());
            ps.setString (2, membre.getPrenom ());
            ps.setDate (3, Date.valueOf (membre.getDateNaissance ()));
            ps.setString (4, membre.getLieuNaissance ());
            ps.setString (5, membre.getTelephone ());
            ps.setString (6, membre.getEmail ());
            ps.setBinaryStream (7, membre.getPhoto ());
            ps.setString (8, membre.getAdresse ());
            ps.setString (9, membre.getProfession ());
            ps.setString (10, membre.getExtra ());
            ps.setInt (11, membre.getId ());

            r = ps.executeUpdate ();

            /*ResultSet rs = con.createStatement ().executeQuery (query);
            while (rs.next ()) {
                id = rs.getInt (1);
            }*/
            ps = con.prepareStatement (MODIFIERBAPTEME);
            ps.setString (1, bapteme.getLieuBapteme ());
            ps.setDate (2, Date.valueOf (bapteme.getDateBapteme ()));
            ps.setString (3, bapteme.getPasteur ());
            ps.setString (4, bapteme.getEgliseDest ());
            ps.setString (5, bapteme.getEglisePro ());
            ps.setDate (6, Date.valueOf (bapteme.getDateTransfert ()));
            ps.setInt (7, membre.getId ());

            r1 = ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
            Alert alert = new Alert (Alert.AlertType.ERROR, e.getMessage ());
            alert.initModality (Modality.APPLICATION_MODAL);
            alert.show ();
        }

        return r + r1;
    }

    @Override
    public int delete(Membre membre) {
        int r = 0;
        try {
            PreparedStatement ps = con.prepareStatement (DELETEMEMBRE);
            ps.setInt (1, membre.getId ());

            r = ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return r;
    }

    @Override
    public int add(Membre membre, Bapteme bapteme) {

        int id = 0, r = 0, r1 = 0;
        try {
           /* if (!DBUtil.schemaExists(con)) {
                try {
                    DBUtil.createSchema(con);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }*/
            PreparedStatement ps = con.prepareStatement (AJOUTERMEMBRE);
            ps.setString (1, membre.getNom ());
            ps.setString (2, membre.getPrenom ());
            ps.setDate (3, Date.valueOf (membre.getDateNaissance ()));
            ps.setString (4, membre.getLieuNaissance ());
            ps.setString (5, membre.getTelephone ());
            ps.setString (6, membre.getEmail ());
            ps.setBinaryStream (7, membre.getPhoto ());
            ps.setString (8, membre.getSexe ());
            ps.setString (9, membre.getAdresse ());
            ps.setString (10, membre.getProfession ());
            ps.setString (11, membre.getExtra ());
            ps.setString (12, membre.getSituationM ());

            r = ps.executeUpdate ();

            ResultSet rs = con.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery ("SELECT id FROM membre WHERE nom = " + "'" + membre.getNom () + "'" + " AND prenom = " + "'" + membre.getPrenom () + "'");

            while (rs.next ()) {
                id = rs.getInt (1);
            }

            ps = con.prepareStatement (AJOUTERBAPTEME);
            ps.setString (1, bapteme.getLieuBapteme ());
            ps.setDate (2, Date.valueOf (bapteme.getDateBapteme ()));
            ps.setString (3, bapteme.getPasteur ());
            ps.setString (4, bapteme.getEgliseDest ());
            ps.setString (5, bapteme.getEglisePro ());
            ps.setDate (6, Date.valueOf (bapteme.getDateTransfert ()));
            ps.setInt (7, id);

            r1 = ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
            Alert alert = new Alert (Alert.AlertType.ERROR, e.getMessage ());
            alert.initModality (Modality.APPLICATION_MODAL);
            alert.show ();
        }

        return r + r1;
    }

    @Override
    public ObservableList<Membre> getList(String status) {
        try {
            membreList = FXCollections.observableArrayList ();
            PreparedStatement ps = con.prepareStatement (INFOSMEMBRE);
            ps.setString (1, status);
            ResultSet rs = ps.executeQuery ();
//            ResultSet rs = con.createStatement ().executeQuery (INFOSMEMBRE);
            while (rs.next ()) {
                Membre membre = new Membre ();
                membre.setId (rs.getInt (1));
                membre.setNom (rs.getString (2));
                membre.setPrenom (rs.getString (3));
                membre.setSexe (rs.getString (4).substring (0, 1).toUpperCase ());
                membre.setDateNaissance (rs.getDate (5).toLocalDate ());
                membre.setSituationM (rs.getString (6));
                membre.setAdresse (rs.getString (7));
                membre.setProfession (rs.getString (8));
                membre.setTelephone (rs.getString (9));
                membre.setEmail (rs.getString (10));
                membreList.add (membre);
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return membreList;
    }

  /*  public ObservableList<Membre> getMembreInactiveList() {
        try {
            membreList = FXCollections.observableArrayList ();
            ResultSet rs = con.createStatement ().executeQuery (INFOSMEMBREINACTIVE);
            while (rs.next ()) {
                Membre membre = new Membre ();
                membre.setId (rs.getInt (1));
                membre.setNom (rs.getString (2));
                membre.setPrenom (rs.getString (3));
                membre.setSexe (rs.getString (4).substring (0, 1).toUpperCase ());
                membre.setDateNaissance (rs.getDate (5).toLocalDate ());
                membre.setSituationM (rs.getString (6));
                membre.setAdresse (rs.getString (7));
                membre.setProfession (rs.getString (8));
                membre.setTelephone (rs.getString (9));
                membre.setEmail (rs.getString (10));
                membreList.add (membre);
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return membreList;
    }*/

    @Override
    public int changeStatus(Membre membre, String status) {
        int i = 0;
        try {
            PreparedStatement ps = con.prepareStatement (CHANGESTATUS);
            ps.setString (1, status);
            ps.setInt (2, membre.getId ());

            i = ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return i;
    }

    public ObservableList<Membre> getMembreListWithCriteria(String status, String criteria) throws SQLException {
        membreList = FXCollections.observableArrayList ();
//        ResultSet rs = con.createStatement ().executeQuery (INFOSMEMBREWITHCRITERIA + " " + status + " ORDER BY " + criteria);
        PreparedStatement ps = con.prepareStatement (INFOSMEMBREWITHCRITERIA + "ORDER BY " + criteria);
        ps.setString (1, status);
        ResultSet rs = ps.executeQuery ();
        while (rs.next ()) {
            Membre membre = new Membre ();
            membre.setId (rs.getInt (1));
            membre.setNom (rs.getString (2));
            membre.setPrenom (rs.getString (3));
            membre.setSexe (rs.getString (4).substring (0, 1).toUpperCase ());
            membre.setDateNaissance (rs.getDate (5).toLocalDate ());
            membre.setSituationM (rs.getString (6));
            membre.setAdresse (rs.getString (7));
            membre.setProfession (rs.getString (8));
            membre.setTelephone (rs.getString (9));
            membre.setEmail (rs.getString (10));
            membreList.add (membre);
        }
        return membreList;
    }
}
