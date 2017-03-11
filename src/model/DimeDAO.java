package model;

import DB.DBUtil;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by Ethiel on 10/03/2017.
 */
public class DimeDAO implements DaoCo<Dime> {

    String ajouterDime = "INSERT INTO DIME(membre,montant,sabbat) VALUES(?,?,?)";
    String supprimerDime = "DELETE FROM dime WHERE id =?";
    String modifierDime = "UPDATE TABLE DIME SET membre = ?,montant = ?,sabbat = ? WHERE id = ?";
    String voirDime = "SELECT * FROM dime";
    String voirDimeUnique = "SELECT * FROM dime WHERE id = ?";

    private ResultSet rs = null;
    private Connection con;
    private PreparedStatement ps = null;


    public DimeDAO(Connection con) throws SQLException, ClassNotFoundException {
        this.con = DBUtil.getConnexion();
        if (!DBUtil.schemaExists(con)) {
            try {
                DBUtil.createSchema(con);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public int add(Dime dime) {
        int r = 0;
        try {
            ps = this.con.prepareStatement(ajouterDime);
            ps.setInt(1, dime.getMembre());
            ps.setInt(2, dime.getMontant());
            ps.setDate(3, Date.valueOf(dime.getSabbat()));

            r = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void delete(Dime dime) {

    }

    @Override
    public void update(Dime dime) {

    }

    @Override
    public ObservableList getAll() {
        return null;
    }

    @Override
    public Object getUnique(Dime dime) {
        return null;
    }
}
