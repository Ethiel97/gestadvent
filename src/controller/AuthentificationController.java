package controller;/**
 * Created by Ethiel on 29/12/2016.
 */

import DB.DBUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.LoginManager;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthentificationController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    public AuthentificationController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BooleanBinding bb = new BooleanBinding () {
            {
                super.bind (usernameField.textProperty (), passwordField.textProperty ());
            }

            @Override
            protected boolean computeValue() {
                return (usernameField.getText ().isEmpty () || passwordField.getText ().isEmpty ());
            }
        };

        loginButton.disableProperty ().bind (bb);

        DashboardController.fadeTransition (anchorPane);
    }

    private boolean isValid(String user, String pass) {
        String userCheck = "", passCheck = "";
        try {
            Connection connection = DBUtil.getConnexion ();
            String query = "SELECT * FROM user";
            ResultSet rs = connection.createStatement ().executeQuery (query);
            while (rs.next ()) {
                userCheck = rs.getString (1).trim ();
                passCheck = rs.getString (2).trim ();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
        return ((userCheck.equals (user)) && (passCheck.equals (pass)));
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction ((ActionEvent event) -> {
            String user = usernameField.getText ().trim ();
            String pass = passwordField.getText ().trim ();
            if (isValid (user, pass)) {
                Stage stage = (Stage) anchorPane.getScene ().getWindow ();
                stage.hide ();
                loginManager.showDashboard ();
            } else
                statusLabel.setText ("Veuillez Reessayer !");

        });
    }
}
