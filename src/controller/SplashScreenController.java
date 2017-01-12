package controller;/**
 * Created by Ethiel on 29/11/2016.
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane splashScreenRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();
    }

    public class SplashScreen extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(100);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        assert root != null;
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setOnCloseRequest(this::closeRequest);
                        stage.setResizable(false);
                        stage.show();
                        splashScreenRoot.getScene().getWindow().hide();
                    }

                    private void closeRequest(WindowEvent e) {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("GESTADVENT");
      /*  Image image = new Image("images/logo.png");
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);*/
                        alert.setContentText("Desirez-vous vraiment quitter l'application ?");
                        alert.initModality(Modality.APPLICATION_MODAL);
                        Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                        exitButton.setText("Quitter");
                        exitButton.setMaxWidth(215.0);
      /*  ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getDialogPane().getButtonTypes().clear();
        alert.getDialogPane().getButtonTypes().addAll(noButton, yesButton);*/

                        Optional<ButtonType> result = alert.showAndWait();
                        if (!ButtonType.OK.equals(result.get())) {
                            e.consume();
                        }
       /* if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        } else if ((result.isPresent() && result.get() == ButtonType.NO)) {
            alert.hide();
        }*/
//            else if((result.isPsresent())&&(result.get()== ButtonType.CLOSE))
//                alert.hide();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
