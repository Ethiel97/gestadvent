package controller;/**
 * Created by Ethiel on 05/01/2017.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SermonController implements Initializable {


    @FXML
    private ListView listView;
    @FXML
    private ImageView backHome;
    @FXML
    private ImageView microButton;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DashboardController.fadeTransition (anchorPane);


        Tooltip t1 = new Tooltip ("Cliquez pour commencer a enregister");
        Tooltip.install (microButton, t1);

//        listView.setStyle ("");
    }

    @FXML
    private void backHome(MouseEvent mouseEvent) {
        Scene scene;
        Parent root = null;
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource ()).getScene ().getWindow ();
        try {
            root = FXMLLoader.load (getClass ().getResource ("../view/dashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace ();
        }
        assert root != null;
        scene = new Scene (root);
        stage.setScene (scene);
        stage.show ();
    }

    @FXML
    private void record(MouseEvent mouseEvent) {

        FXMLLoader loader = new FXMLLoader (getClass ().getResource ("../view/recordPanel.fxml"));
        try {
            Parent root = loader.load ();
            Stage stage = new Stage ();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.initModality (Modality.APPLICATION_MODAL);
            stage.initOwner (anchorPane.getScene ().getWindow ());
            stage.setResizable (false);
            stage.show ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}