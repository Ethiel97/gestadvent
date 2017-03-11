package controller;

import DB.DBUtil;
import javafx.collections.ObservableList;
import model.DaoCo;
import model.Offrande;

import java.sql.*;


public class OffrandeDAO implements DaoCo<Offrande> {
    String ajouterOffrande = "INSERT INTO DIME(type,membre,montant,sabbat) VALUES(?,?,?,?)";
    String supprimerOffrande = "DELETE FROM dime WHERE id =?";
    String modifierOffrande = "UPDATE TABLE DIME SET type = ?, membre = ?,montant = ?,sabbat = ? WHERE id = ?";
    String voirOffrande = "SELECT * FROM offrande";
    String voirOffrandeUnique = "SELECT * FROM offrande WHERE id = ?";

    private ResultSet rs = null;
    private Connection con;
    private PreparedStatement ps = null;

    public OffrandeDAO(Connection con) throws SQLException, ClassNotFoundException {
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
    public int add(Offrande offrande) {
        int r = 0;
        try {
            ps = this.con.prepareStatement(ajouterOffrande);
            ps.setString(1, offrande.getType());
            ps.setInt(2, offrande.getMembre());
            ps.setInt(3, offrande.getMontant());
            ps.setDate(4, Date.valueOf(offrande.getSabbat()));

            r = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void delete(Offrande offrande) {

    }

    @Override
    public void update(Offrande offrande) {

    }

    @Override
    public ObservableList getAll() {
        return null;
    }

    @Override
    public Object getUnique(Offrande offrande) {
        return null;
    }
}
