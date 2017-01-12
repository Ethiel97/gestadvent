package controller;/**
 * Created by Ethiel on 29/11/2016.
 */

import DB.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Bapteme;
import model.Membre;
import model.MembreBDAO;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MembreDetailsController implements Initializable {

    @FXML
    private Button deleteButton;
    @FXML
    private TextField idField;
    @FXML
    private CheckBox transfertCheckBox;
    @FXML
    private TextArea photoPathArea;
    @FXML
    private Button photoButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button toggleModifier;
    @FXML
    private Label profil;
    @FXML
    private TextField lieuNaissanceField;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField professionField;
    @FXML
    private TextField lieuBaptemeField;
    @FXML
    private TextField pasteurField;
    @FXML
    private DatePicker dateBaptemePicker;
    @FXML
    private TextField destinationField;
    @FXML
    private TextArea adresseField;
    @FXML
    private TextField provenanceField;
    @FXML
    private DatePicker dateTransfertPicker;
    @FXML
    private TextArea extraField;
    @FXML
    private TextField matriField;
    @FXML
    private Button okButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button printButton;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField sexeField;

    private Membre membre = new Membre ();
    private Bapteme bapteme = new Bapteme ();

    private FileChooser fileChooser = new FileChooser ();
    FileInputStream fis;
    private File file;
    private Connection connection;
    private static String status = "inactive";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        photoPathArea.setVisible(true);
        Tooltip t1 = new Tooltip ("Imprimer la fiche");
        Tooltip.install (printButton, t1);
        Tooltip t2 = new Tooltip ("Modifier les infos");
        Tooltip.install (modifierButton, t2);
        try {
            this.initData (this.membre, this.bapteme);
        } catch (IOException e) {
            e.printStackTrace ();
        }

        photoPathArea.setEditable (false);
        idField.setDisable (true);
        sexeField.setDisable (true);
        matriField.setDisable (true);
        ArrayList<Control> controls = fillControls ();
        for (Control control : controls) {
            control.setDisable (true);
            control.setStyle ("-fx-text-fill: black;");
        }


       /* BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(nomField.disableProperty(), prenomField.disableProperty(), sexeField.disableProperty(),
                        professionField.disableProperty(), emailField.disableProperty(), adresseField.disableProperty(),
                        lieuNaissanceField.disableProperty(), dateNaissancePicker.disableProperty(), extraField.disableProperty(),
                        pasteurField.disableProperty(), dateBaptemePicker.disableProperty(), dateTransfertPicker.disableProperty(),
                        lieuBaptemeField.disableProperty(), destinationField.disableProperty(), provenanceField.disableProperty(),
                        matriField.disableProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nomField)
            }

        };*/

        transfertCheckBox.selectedProperty ().addListener ((observable, oldValue, newValue) -> {
            destinationField.setVisible (newValue);
            provenanceField.setVisible (newValue);
            dateTransfertPicker.setVisible (newValue);
        });

        //make transfert fields invisible
        destinationField.setVisible (true);
        provenanceField.setVisible (true);
        dateTransfertPicker.setVisible (true);

        toggleModifier.setOnAction (event -> {
            ArrayList<Control> controlList = fillControls ();
            for (Control control : controlList) {
                if (control.isDisabled ())
                    control.setDisable (false);
                else
                    control.setDisable (true);
            }
           /* for (Control control : controlList) {
                if (!control.isDisable())
                    control.setDisable(true);
            }*/
        });

        okButton.setOnAction (event -> anchorPane.getScene ().getWindow ().hide ());

    }

    void initData(Membre membre, Bapteme bapteme) throws IOException {
        InputStream is = membre.getPhoto ();
        File file = new File ("photo.png");
        OutputStream os = new FileOutputStream (file);
        BufferedOutputStream bos = new BufferedOutputStream (os);
        byte[] content = new byte[8192];
        int size;
        while ((size = is.read (content)) != -1) {
            bos.write (content, 0, size);
        }
//        bos.close ();
//        os.close ();
//        is.close ();

        Image profilImage = new Image ("file:photo.png", 169, 169, true, true);
        ImageView profilImageView = new ImageView ();
        profilImageView.setImage (profilImage);
        profilImageView.setFitWidth (169);
        profilImageView.setFitHeight (169);
        idField.setText ((String.valueOf (membre.getId ())));
        nomField.setText (membre.getNom ());
        prenomField.setText (membre.getPrenom ());
        sexeField.setText (membre.getSexe ());
        emailField.setText (membre.getEmail ());
        professionField.setText (membre.getProfession ());
        adresseField.setText (membre.getAdresse ());
        phoneField.setText (membre.getTelephone ());
        profil.setGraphic (profilImageView);
        lieuNaissanceField.setText (membre.getLieuNaissance ());
        dateNaissancePicker.setValue (membre.getDateNaissance ());
        extraField.setText (membre.getExtra ());
        matriField.setText (membre.getSituationM ());
        System.out.println (bapteme.getPasteur ());
        pasteurField.setText (bapteme.getPasteur ());
        destinationField.setText (bapteme.getEgliseDest ());
        provenanceField.setText (bapteme.getEglisePro ());
        lieuBaptemeField.setText (bapteme.getLieuBapteme ());
        dateBaptemePicker.setValue (bapteme.getDateBapteme ());
        dateTransfertPicker.setValue (bapteme.getDateTransfert ());
        lieuBaptemeField.setText (bapteme.getLieuBapteme ());
        photoPathArea.setText (file.getAbsolutePath ());
    }

    private ArrayList<Control> fillControls() {
        ArrayList<Control> fieldArrayList = new ArrayList<> ();
        fieldArrayList.add (nomField);
        fieldArrayList.add (prenomField);
//        fieldArrayList.add(sexeField);
        fieldArrayList.add (professionField);
        fieldArrayList.add (adresseField);
        fieldArrayList.add (extraField);
        fieldArrayList.add (lieuNaissanceField);
        fieldArrayList.add (dateNaissancePicker);
        fieldArrayList.add (emailField);
//        fieldArrayList.add(matriField);
        fieldArrayList.add (phoneField);
        fieldArrayList.add (dateBaptemePicker);
        fieldArrayList.add (dateTransfertPicker);
        fieldArrayList.add (pasteurField);
        fieldArrayList.add (lieuBaptemeField);
        fieldArrayList.add (destinationField);
        fieldArrayList.add (provenanceField);
        return fieldArrayList;
    }

    @FXML
    public void updateMember(ActionEvent event) throws FileNotFoundException {
        try {
            String title = "GESTADVENT NOTIFICATION", message;
            int result;
            TrayNotification tray = new TrayNotification ();
            connection = DBUtil.getConnexion ();
            MembreBDAO membreBDAO = new MembreBDAO (connection);
            Membre membre = new Membre ();
            membre.setId (Integer.valueOf (idField.getText ()));
            membre.setNom (nomField.getText ());
            membre.setSexe (sexeField.getText ());
            membre.setPrenom (prenomField.getText ());
            membre.setAdresse (adresseField.getText ());
            membre.setExtra (extraField.getText ());
            membre.setLieuNaissance (lieuNaissanceField.getText ());
            membre.setDateNaissance (dateNaissancePicker.getValue ());
            membre.setEmail (emailField.getText ());
            membre.setTelephone (phoneField.getText ());
            membre.setProfession (professionField.getText ());
            membre.setPhoto (new FileInputStream (new File (photoPathArea.getText ())));
            membre.setSituationM (matriField.getText ());

            Bapteme bapteme = new Bapteme (dateBaptemePicker.getValue (), lieuBaptemeField.getText (), pasteurField.getText (), destinationField.getText (), provenanceField.getText (), dateTransfertPicker.getValue ());

            result = membreBDAO.update (membre, bapteme);
            if (result <= 2) {
                message = "Les informations de " + membre.getNom () + " " + membre.getPrenom ().toUpperCase () + " ont ete bien modifiees!";
                tray.setTitle (title);
//                tray.setImage(new Image(photoPathArea.getText()));
                tray.setMessage (message);
                tray.setNotificationType (NotificationType.SUCCESS);
                tray.setAnimationType (AnimationType.SLIDE);
                tray.showAndDismiss (Duration.millis (6500.0));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    public void openDialog(ActionEvent event) throws MalformedURLException {

        Image image;
        Stage stage = (Stage) photoButton.getScene ().getWindow ();
        fileChooser.setTitle ("Choisir la photo");
        ImageView imageView;

        String userDirectoryString = System.getProperty ("user.home");
        File userDirectory = new File (userDirectoryString);
        if (!userDirectory.canRead ()) {
            userDirectory = new File ("c:/");
        }
        fileChooser.setInitialDirectory (userDirectory);
        fileChooser.setInitialFileName ("GestAdvent profil");
        fileChooser.getExtensionFilters ().addAll (new FileChooser.ExtensionFilter ("Fichiers images", "*png", "*jpg", "*jpeg"));
        fileChooser.setSelectedExtensionFilter (fileChooser.getExtensionFilters ().get (0));
        file = fileChooser.showOpenDialog (stage);
        if (file != null) {
//            String path = file.getPath();
            image = new Image (file.toURI ().toURL ().toString (), 169, 169, true, true);
            imageView = new ImageView (image);
            imageView.setFitWidth (169);
            imageView.setFitHeight (169);
            profil.setGraphic (imageView);
            photoPathArea.clear ();
            photoPathArea.setText (file.getAbsolutePath ());
        }
    }

    @FXML
    public void deleteMember(ActionEvent event) {

        String title = "GESTADVENT NOTIFICATION", message;
        TrayNotification tray = new TrayNotification ();
        Stage stage = (Stage) deleteButton.getScene ().getWindow ();
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setHeaderText ("GESTADVENT");
        alert.setTitle ("Confirmation");
        alert.initOwner (stage);
        alert.initModality (Modality.APPLICATION_MODAL);
        alert.setContentText ("Voulez-vous vraiment supprimer " + nomField.getText () + " " + prenomField.getText () + " ?");

        Button exitButton = (Button) alert.getDialogPane ().lookupButton (ButtonType.OK);
        exitButton.setText ("Oui");
        Optional<ButtonType> result = alert.showAndWait ();
        if (ButtonType.OK.equals (result.get ())) {
            int i;
            try {
                connection = DBUtil.getConnexion ();
                Membre membre = new Membre ();
                membre.setId (Integer.valueOf (idField.getText ()));
                membre.setNom (nomField.getText ());
                membre.setSexe (sexeField.getText ());
                membre.setPrenom (prenomField.getText ());
               /* membre.setAdresse(adresseField.getText());
                membre.setExtra(extraField.getText());
                membre.setLieuNaissance(lieuNaissanceField.getText());
                membre.setDateNaissance(dateNaissancePicker.getValue());
                membre.setEmail(emailField.getText());
                membre.setTelephone(phoneField.getText());
                membre.setProfession(professionField.getText());
                membre.setPhoto(new FileInputStream(new File(photoPathArea.getText())));
                membre.setSituationM(matriField.getText());
                Bapteme bapteme = new Bapteme(dateBaptemePicker.getValue(), lieuBaptemeField.getText(), pasteurField.getText(), destinationField.getText(), provenanceField.getText(), dateTransfertPicker.getValue());
                */
                MembreBDAO membreBDAO = new MembreBDAO (connection);
                i = membreBDAO.changeStatus (membre, status);
                if (1 == i) {
                    message = "Le Statut de " + membre.getNom () + " " + membre.getPrenom ().toUpperCase () + " a bien change!";
                    tray.setTitle (title);
                    tray.setMessage (message);
                    tray.setNotificationType (NotificationType.SUCCESS);
                    tray.setAnimationType (AnimationType.SLIDE);
                    tray.showAndDismiss (Duration.millis (6500.0));
                } else {
                    System.out.println ("Error");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace ();
            }
        } else
            alert.close ();

    }
}