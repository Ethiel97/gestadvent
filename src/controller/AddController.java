package controller;/*
  Created by Dell on 07/10/2016.
 */

import DB.DBUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Bapteme;
import model.Membre;
import model.MembreBDAO;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class AddController implements Initializable {
    private static final Logger logger = Logger.getLogger(AddController.class.getName());
    private final FileChooser fileChooser = new FileChooser();
    private final ObservableList<String> sexeList = FXCollections.observableArrayList("Masculin", "Feminin", "Indetermine");
    private final ObservableList<String> matriList = FXCollections.observableArrayList("Celibataire", "Fiance", "Marie", "Divorce", "Veuf");
    FileInputStream fis;
    @FXML
    private Button resetProfil;
    @FXML
    private TextArea photoPathArea;
    @FXML
    private DatePicker dateTransfertPicker;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView backHome;
    @FXML
    private TextArea adresseField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField provenanceField;
    @FXML
    private TextField professionField;
    @FXML
    private TextArea extraField;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private DatePicker dateBaptemePicker;
    @FXML
    private TextField lieuBaptemeField;
    @FXML
    private CheckBox transfertCheckBox;
    @FXML
    private Label profil;
    @FXML
    private Button photoButton;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField pasteurField;
    @FXML
    private TextField lieuNaissanceField;
    @FXML
    private ComboBox<String> sexeComboBox;
    @FXML
    private Button annulerButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private ComboBox<String> matriComboBox;
    private File file;

    @FXML
    private void openDialog() throws MalformedURLException {
        Image image;
        Stage stage = (Stage) photoButton.getScene().getWindow();
        fileChooser.setTitle("Choisir la photo");
        ImageView imageView;

        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);
        fileChooser.setInitialFileName("GestAdvent profil");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers images", "*png", "*jpg", "*jpeg"));
        fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
//            String path = file.getPath();

            image = new Image(file.toURI().toURL().toString(), 200, 191, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(191);
            profil.setGraphic(imageView);
            photoPathArea.setText(file.getAbsolutePath());
            photoPathArea.setEditable(false);
        }
//        return file;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DashboardController.fadeTransition(anchorPane);

        //standard initializations
        Tooltip t = new Tooltip("Cliquez ici pour retourner au Dashboard");
        Tooltip.install(backHome, t);
        backHome.setCursor(Cursor.HAND);
        ajouterButton.setDisable(true);
        ajouterButton.setDefaultButton(true);
        transfertCheckBox.setSelected(true);
        sexeComboBox.getItems().setAll(sexeList);
        matriComboBox.getItems().setAll(matriList);

        dateTransfertPicker.setValue(LocalDate.of(2010, 2, 10));
//        anchorPane.setEffect(new InnerShadow());


        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(nomField.textProperty(), prenomField.textProperty(), adresseField.textProperty(),
                        professionField.textProperty(), lieuNaissanceField.textProperty(), lieuBaptemeField.textProperty(),
                        pasteurField.textProperty(), telephoneField.textProperty(), emailField.textProperty(), sexeComboBox.valueProperty(),
                        profil.graphicProperty(), dateBaptemePicker.valueProperty(), matriComboBox.valueProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nomField.getText().isEmpty() || prenomField.getText().isEmpty()
                        || emailField.getText().isEmpty() || telephoneField.getText().isEmpty()
                        || adresseField.getText().isEmpty() || lieuBaptemeField.getText().isEmpty()
                        || pasteurField.getText().isEmpty() || lieuNaissanceField.getText().isEmpty()
                        || sexeComboBox.getValue() == null || dateBaptemePicker.getValue() == null
                        || profil.getGraphic() == null || professionField.getText().isEmpty()
                        || matriComboBox.getValue() == null
                );
            }
        };

        ajouterButton.disableProperty().bind(bb);


        transfertCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            destinationField.setVisible(newValue);
            provenanceField.setVisible(newValue);
            dateTransfertPicker.setVisible(newValue);
        });

        //make transfert fields invisible
        destinationField.setVisible(true);
        provenanceField.setVisible(true);
        dateTransfertPicker.setVisible(true);

    }

    @FXML
    public void ajouterMembre(ActionEvent actionEvent) {
        logger.info("bouton Ajouter clique");
        try {

            String title = "GESTADVENT NOTIFICATION", message;
            int result;
            TrayNotification tray = new TrayNotification();
            Connection connection = DBUtil.getConnexion();
            MembreBDAO membreBDAO = new MembreBDAO(connection);


            Membre membre = new Membre();
            membre.setNom(nomField.getText());
            membre.setPrenom(prenomField.getText());
            membre.setAdresse(adresseField.getText());
            membre.setExtra(extraField.getText());
            membre.setLieuNaissance(lieuNaissanceField.getText());
            membre.setDateNaissance(dateNaissancePicker.getValue());
            membre.setSexe(sexeComboBox.getSelectionModel().getSelectedItem());
            membre.setEmail(emailField.getText());
            membre.setTelephone(telephoneField.getText());
            membre.setProfession(professionField.getText());
            membre.setPhoto(new FileInputStream(new File(photoPathArea.getText())));
            membre.setSituationM(matriComboBox.getSelectionModel().getSelectedItem());

            Bapteme bapteme = new Bapteme(dateBaptemePicker.getValue(), lieuBaptemeField.getText(), pasteurField.getText(), destinationField.getText(), provenanceField.getText(), dateTransfertPicker.getValue());
//            Bapteme bapteme = new Bapteme();
//            bapteme.setDateBapteme(dateBaptemePicker.getValue());
//            bapteme.setLieuBapteme(lieuBaptemeField.getText());
//            bapteme.setPasteur(pasteurField.getText());
//            bapteme.setEgliseDest(destinationField.getText());
//            bapteme.setEglisePro(provenanceField.getText());...
          /*  Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Debug");
            Button button = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            button.setText("OK");
            alert.setContentText(bapteme.getPasteur()+" est le pasteur officiant");
            alert.show();
*/
            result = membreBDAO.add(membre, bapteme);
            if (1 < result) {
                message = membre.getNom() + " " + membre.getPrenom().toUpperCase() + " bien enregistre(e)!";
                tray.setTitle(title);
//                tray.setImage(new Image(photoPathArea.getText()));
                tray.setMessage(message);
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.setAnimationType(AnimationType.SLIDE);
                tray.showAndDismiss(Duration.millis(6500.0));

                clearFields();
            }

        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        }
//        Membre membre = new Membre();
    }

    @FXML
    public void annuler(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        ArrayList<TextInputControl> fieldArrayList = fillList();
        fieldArrayList.forEach(TextInputControl::clear);
        profil.setGraphic(null);
        sexeComboBox.setPromptText("Votre sexe");
        matriComboBox.setPromptText("Situation matrimoniale");
        dateNaissancePicker.setPromptText("Date de bapteme");
        dateBaptemePicker.setPromptText("Date de bapteme");
        dateTransfertPicker.setPromptText("Date de transfert");
    }

    private ArrayList<TextInputControl> fillList() {
        ArrayList<TextInputControl> fieldArrayList = new ArrayList<>();
        fieldArrayList.add(nomField);
        fieldArrayList.add(prenomField);
        fieldArrayList.add(emailField);
        fieldArrayList.add(destinationField);
        fieldArrayList.add(pasteurField);
        fieldArrayList.add(lieuBaptemeField);
        fieldArrayList.add(lieuNaissanceField);
        fieldArrayList.add(destinationField);
        fieldArrayList.add(professionField);
        fieldArrayList.add(telephoneField);
        fieldArrayList.add(provenanceField);
        fieldArrayList.add(adresseField);
        fieldArrayList.add(extraField);
        fieldArrayList.add(photoPathArea);
        return fieldArrayList;
    }

    @FXML
    public void backHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((ImageView)mouseEvent.getSource ()).getScene ().getWindow ();
        Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void resetProfil(ActionEvent actionEvent) {
        profil.setGraphic(null);
    }
}