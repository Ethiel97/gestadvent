package login;

import controller.AuthentificationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Ethiel on 30/12/2016.
 */
public class LoginManager {

    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader (getClass ().getResource ("../view/dashboard.fxml"));
            scene.setRoot (loader.load ());
            Stage stage = new Stage ();
            stage.setScene (scene);
            stage.setResizable (false);
            stage.setTitle ("GESTADVENT");
            stage.centerOnScreen ();
            stage.setOnCloseRequest (e -> {

                Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                alert.setHeaderText ("GESTADVENT");
                alert.setContentText ("Desirez-vous vraiment quitter l'application ?");
                alert.initOwner (stage);
                alert.initModality (Modality.APPLICATION_MODAL);
                Button exitButton = (Button) alert.getDialogPane ().lookupButton (ButtonType.OK);
                exitButton.setText ("Quitter GESTADVENT");
                Optional<ButtonType> result = alert.showAndWait ();
                if (!ButtonType.OK.equals (result.get ())) {
                    e.consume ();
                }
            });
            stage.show ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader (getClass ().getResource ("../view/authentification.fxml"));
//            scene = new Scene (loader.load ());
            scene.setRoot (loader.load ());
            AuthentificationController controller = loader.getController ();
            controller.initManager (this);
            Stage stage = new Stage ();
            stage.setScene (scene);
            stage.setOnCloseRequest (e -> {
                Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                alert.setHeaderText ("AUTHENTIFICATION");
                alert.setContentText ("Desirez-vous vraiment quitter l'application ?");
                alert.initOwner (stage);
                alert.initModality (Modality.WINDOW_MODAL);
                Button exitButton = (Button) alert.getDialogPane ().lookupButton (ButtonType.OK);
                exitButton.setText ("Quitter GESTADVENT");
                Optional<ButtonType> result = alert.showAndWait ();
                if (!ButtonType.OK.equals (result.get ())) {
                    e.consume ();
                }
            });
            stage.centerOnScreen ();
            stage.setResizable (false);
            stage.show ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
