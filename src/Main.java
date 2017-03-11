import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.LoginManager;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        this.primaryStage = primaryStage;
//        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/splashScreen.fxml"));

      /*  AnchorPane rootPane = (AnchorPane) root.lookup("#rootPane");
        rootPane.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0) !important;" +
                "-fx-background-color: white !important;");
*/
        Scene scene = new Scene(new AnchorPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

       /* primaryStage.setTitle ("GESTADVENT");
        primaryStage.centerOnScreen ();*/
//        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
       /* primaryStage.setScene (scene);
        primaryStage.show ();*/
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    public static void main(String[] args) {

//        LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
        launch(args);
    }
}
