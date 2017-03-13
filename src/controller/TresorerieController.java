package controller;/**
 * Created by Ethiel on 08/03/2017.
 */

import DB.DBUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class TresorerieController implements Initializable {
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());
    @FXML
    private ImageView backHome;
    @FXML
    private Button annulerButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private TextField montantField;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private DatePicker sabbatField;
    @FXML
    private ComboBox<String> membreField;
    @FXML
    private AnchorPane anchorPane;

    private Connection con = null;
    MembreBDAO membreBDAO = null;
    DimeDAO dimeDAO = null;
    OffrandeDAO offrandeDAO = null;
    TrayNotification tray = new TrayNotification();
    private final ObservableList<String> typeList = FXCollections.observableArrayList("Dime", "Offrandes");


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            con = DBUtil.getConnexion();
            membreBDAO = new MembreBDAO(con);
            ObservableList<Membre> membreList = membreBDAO.populateChoiceBox();

            ObservableList<String> d = FXCollections.observableArrayList();
            membreList.forEach(membre -> {
                d.add(membre.getId() + " " + membre.getNom() + " " + membre.getPrenom());
            });
            membreField.getItems().addAll(d);
            typeField.getItems().setAll(typeList);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
//       dimeDAO = getConnection();
        DashboardController.fadeTransition(anchorPane);

        BooleanBinding bb = new BooleanBinding() {

            {
                super.bind(montantField.textProperty(), sabbatField.valueProperty(), typeField.valueProperty(), membreField.valueProperty());
            }

            @Override
            protected boolean computeValue() {
                return (montantField.getText().isEmpty() || sabbatField.getValue() == null || typeField.getValue() == null ||
                        membreField.getValue() == null);
            }
        };

        ajouterButton.disableProperty().bind(bb);

    }

    Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getConnexion();
        return con;
    }

    private void clearFields() {
        ArrayList<TextInputControl> fieldArrayList = fillList();
        fieldArrayList.forEach(TextInputControl::clear);
        sabbatField.setPromptText("Date du Sabbat");
        montantField.setPromptText("15000");
        typeField.setPromptText("Dime/Offrandes");
        typeField.setPromptText("Choisir un membre");
    }

    private ArrayList<TextInputControl> fillList() {
        ArrayList<TextInputControl> fieldArrayList = new ArrayList<>();
        fieldArrayList.add(montantField);
        return fieldArrayList;
    }

    @FXML
    public void ajouterCotisation(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        switch (typeField.getValue()) {
            case "Dime": {
                String message;
//                String[] montant1 = montantField.getText().split(" ");
                int montant = Integer.parseInt((montantField.getText().split(" ")[0]));
                LocalDate sabbat = sabbatField.getValue();
                String[] id = membreField.getValue().split(" ");

                int membre = Integer.valueOf(id[0]);
                System.out.println(Integer.valueOf(membre));
                System.out.println(Integer.valueOf(montant));
                Dime dime = new Dime(membre, montant, sabbat);
                con = DBUtil.getConnexion();
                dimeDAO = new DimeDAO(con);
                if (dimeDAO.add(dime) > 0) {
                    message = "Dime de " + membreField.getValue() + " bien enregistree";
                    tray.setMessage(message);
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.setAnimationType(AnimationType.SLIDE);
                    tray.showAndDismiss(Duration.millis(6500.0));
                }
                break;
            }
            case "Offrandes": {
                String message;
                int montant = Integer.valueOf(montantField.getText());
                String type = typeField.getValue();
                LocalDate sabbat = sabbatField.getValue();
                int membre = Integer.valueOf(membreField.getValue().substring(0, 1));
                Offrande offrande = new Offrande(type, membre, montant, sabbat);
                con = DBUtil.getConnexion();
                offrandeDAO = new OffrandeDAO(con);

                if (offrandeDAO.add(offrande) > 0) {
                    message = "Offrande de" + offrande.getMembre() + " bien enregistree";
                    tray.setMessage(message);
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.setAnimationType(AnimationType.SLIDE);
                    tray.showAndDismiss(Duration.millis(6500.0));
                }
                break;
            }
        }
    }

    @FXML
    public void annuler(ActionEvent actionEvent) {
        clearFields();
    }

    @FXML
    public void backHome(MouseEvent mouseEvent) {

        Stage stage = (Stage) backHome.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}