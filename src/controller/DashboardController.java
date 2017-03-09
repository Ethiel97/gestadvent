package controller;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());


    @FXML
    private ImageView powerButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem add;
    @FXML
    private MenuItem modify;
    @FXML
    private MenuItem delete;
    @FXML
    private MenuItem search;
    @FXML
    private MenuItem stats;
    @FXML
    private MenuItem help;
    @FXML
    private VBox addButton;
    @FXML
    private VBox consultButton;
    @FXML
    private VBox deleteButton;
    @FXML
    private VBox sermonButton;
    @FXML
    private VBox statsButton;

    double pressedX;
    double pressedY;
    double draggedX;
    double draggedY;

    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    @FXML
    private void exitApplication() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("GESTADVENT");
      /*  Image image = new Image("images/logo.png");
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);*/
        alert.setContentText("Desirez-vous vraiment quitter l'application ?");
        alert.initOwner(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        exitButton.setText("Quitter GESTADVENT");

        Optional<ButtonType> result = alert.showAndWait();
        if (ButtonType.OK.equals(result.get())) {
            Platform.exit();
        } else
            alert.close();
    }

    @FXML
    private void toggleAdd() {

     /*   URL url = MainController.class.getResource("view/addPanel.fxml");
        try {
            AnchorPane anchorPane = FXMLLoader.load(url);
            Scene scene = new Scene(anchorPane);
            this.primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @FXML
    private void toggleModify() {

    }

    @FXML
    private void toggleDelete() {

    }

    @FXML
    private void toggleSearch() {

    }

    @FXML
    private void toggleStats() {

    }

    @FXML
    private void showHelpDialog(ActionEvent event) {
//        Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
        String title = "Infos GESTADVENT";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.initOwner(stage);
        alert.setTitle(title);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText("A propos de GESTADVENT");
        alert.setContentText("Application de Gestion de membres d'eglise \n developpee par Ethiel ADIASSA \n pour une gestion plus efficiente des membres d'eglise adventiste.");
        alert.showAndWait();

    }

   /* @FXML
    private void rotateButton(Event e) {
        VBox button = (VBox) e.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(800), button);

        scaleTransition.setAutoReverse(true);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);
        scaleTransition.play();
       *//* rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setByAngle(360);
        rt.setCycleCount(0);
        rt.setAutoReverse(false);
        rt.setInterpolator(Interpolator.EASE_OUT);
        rt.play();*//*

    }*/

    @FXML
    private Stage switchTo(Event e) throws IOException {
        Stage stage = null;
        Parent root = null;
        Node node = (Node) e.getSource();
        if (node == addButton) {
            logger.info("addButton is clicked");
            stage = (Stage) addButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/addPanel.fxml"));
        } else if (node == consultButton) {
            logger.info("consultButton is clicked");
            stage = (Stage) consultButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/consultPanel.fxml"));
        } else if (node == deleteButton) {
            logger.info("deleteButton is clicked");
            stage = (Stage) deleteButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/deletePanel.fxml"));
        } else if (node == sermonButton) {
            logger.info("sermonButton is clicked");
            stage = (Stage) sermonButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/sermonPanel.fxml"));
        } else if (node == statsButton) {
            logger.info("statsButton is clicked");
            stage = (Stage) statsButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/statsPanel.fxml"));
        }
        Scene scene = null;
        if (root != null) scene = new Scene(root);
        if (stage != null) stage.setScene(scene);
        if (stage != null) stage.show();
        return stage;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fadeTransition(anchorPane);
//        anchorPane.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 2.0, 15.0, 8.0, 8.0));
        //anchorPane.setBackground(new Background(new BackgroundFill(Color.RED,new CornerRadii(1),new Insets(0))));

        exit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCodeCombination.CONTROL_DOWN));
    }

    public static void fadeTransition(AnchorPane node) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), node);
        ft.setFromValue(0.6);
        ft.setToValue(1);
        ft.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition st = new ScaleTransition(Duration.millis(300), node);
//        scaleTransition.setFrX(1.1);
       /* scaleTransition.setByY(1.1);
        scaleTransition.setByX(1.1);*/
//        st.setCycleCount(1);
        st.setByX(1.004);
        st.setByY(1.004);
        st.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition st1 = new ScaleTransition(Duration.millis(600), node);

        st1.setFromX(1.004);
        st1.setFromY(1.004);
        st1.setToX(1.0);
        st1.setToY(1.0);
        st1.setInterpolator(Interpolator.EASE_OUT);


        SequentialTransition sTr = new SequentialTransition(node, st, st1, ft);
        sTr.setInterpolator(Interpolator.EASE_OUT);
        sTr.play();
       /* parallelTransition.setOnFinished(event -> {
            scaleTransition.setByX(1.3);
            scaleTransition.setByY(1.3);
            scaleTransition.play();
        });*/

      /*  Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(((AnchorPane) node).prefWidthProperty(), 0.0, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(300), new KeyValue(((AnchorPane) node).prefWidthProperty(), prefWidth, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.opacityProperty(), 100.0, Interpolator.EASE_BOTH))
        );
        timeline.play();*/
    }

    @FXML
    public void startMoveWindow(MouseEvent evt) {
//        startMoveX = evt.getScreenX();
//        startMoveY = evt.getScreenY();
      /*  dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(anchorPane.getWidth());
        moveTrackingRect.setHeight(anchorPane.getHeight());
        moveTrackingRect.setFill(Color.WHITESMOKE);
        moveTrackingRect.getStyleClass().add("tracking-rect");

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(anchorPane.getScene().getWindow());
        moveTrackingPopup.setOnHidden((e) -> resetMoveOperation());*/
        pressedX = evt.getX();
        pressedY = evt.getY();
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }

    @FXML
    public void moveWindow(MouseEvent evt) {

       /* if (dragging) {

            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = anchorPane.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }*/

        Stage theStage = (Stage) ((AnchorPane) evt.getSource()).getScene().getWindow();
        draggedX = evt.getX();
        draggedY = evt.getY();

        double differenceX = draggedX - pressedX;
        double differenceY = draggedY - pressedY;

        theStage.setX(theStage.getX() + differenceX);
        theStage.setY(theStage.getY() + differenceY);

    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = anchorPane.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));

            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }

        resetMoveOperation();
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Label) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
