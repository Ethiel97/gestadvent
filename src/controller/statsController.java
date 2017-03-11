package controller;

import DB.DBUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Membre;
import model.MembreBDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Ethiel on 07/02/2017.
 */
public class statsController implements Initializable {
    @FXML
    private TextField maxNom;
    @FXML
    private TextField maxPrenom;
    @FXML
    private TextField maxAge;
    @FXML
    private TextField minNom;
    @FXML
    private TextField minPrenom;
    @FXML
    private TextField minAge;
    @FXML
    private PieChart maritalPieChart;
    @FXML
    private PieChart agePieChart;
    @FXML
    private PieChart sexePieChart;
    @FXML
    private ImageView backHome;
    @FXML
    private AnchorPane anchorPane;

    private ObservableList<PieChart.Data> ageStats;
    private ObservableList<PieChart.Data> maritalStats;
    private ObservableList<PieChart.Data> sexeStats;

    private ObservableList<Membre> membres;
    private MembreBDAO membreBDAO;
    private Connection con;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DashboardController.fadeTransition(anchorPane);
        ArrayList<TextInputControl> fieldArrayList = fillList();
        fieldArrayList.forEach(textInputControl -> {
            textInputControl.setEditable(false);
            textInputControl.setDisable(true);
        });

        agePieChart.setLegendSide(Side.BOTTOM);
        maritalPieChart.setLegendSide(Side.BOTTOM);
        try {
            con = DBUtil.getConnexion();
            membreBDAO = new MembreBDAO(con);
            ageStats = membreBDAO.getPieChartData("AGE");
            maritalStats = membreBDAO.getPieChartData("MARITAL");
            sexeStats = membreBDAO.getPieChartData("SEXE");
            Platform.runLater(() -> {
                agePieChart.setData(ageStats);
                maritalPieChart.setData(maritalStats);
                sexePieChart.setData(sexeStats);
            });
           /* agePieChart.setData(ageStats);
            maritalPieChart.setData(maritalStats);
            sexePieChart.setData(sexeStats);*/
            this.addSliceTooltip(agePieChart);
            this.addSliceTooltip(maritalPieChart);
            this.addSliceTooltip(sexePieChart);

            membres = membreBDAO.getExtrema();
            maxNom.setText(membres.get(0).getNom());
            maxPrenom.setText(membres.get(0).getPrenom());
            maxAge.setText(String.valueOf(membres.get(0).getAge()) + " ans");

            minNom.setText(membres.get(1).getNom());
            minPrenom.setText(membres.get(1).getPrenom());
            minAge.setText(String.valueOf(membres.get(1).getAge()) + " ans");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void backHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) backHome.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addSliceTooltip(PieChart chart) {
        //get each Pie slie value
        double totalPieValue = 0.0;
        for (PieChart.Data d : chart.getData())
            totalPieValue += d.getPieValue();

        for (PieChart.Data d : chart.getData()) {
            Node sliceNode = d.getNode();
            double pieValue = d.getPieValue();
            double percentPieValue = (pieValue / totalPieValue) * 100;

            String msg = d.getName() + "=" + pieValue +
                    " (" + String.format("%.2f", percentPieValue) + "%)";
            Tooltip tt = new Tooltip(msg);
            tt.setStyle("-fx-background-color: #e0da2e;" +
                    "-fx-text-fill: black;");
            Tooltip.install(sliceNode, tt);
        }

    }

    private ArrayList<TextInputControl> fillList() {
        ArrayList<TextInputControl> fieldArrayList = new ArrayList<>();
        fieldArrayList.add(minNom);
        fieldArrayList.add(minPrenom);
        fieldArrayList.add(minAge);
        fieldArrayList.add(maxNom);
        fieldArrayList.add(maxPrenom);
        fieldArrayList.add(maxAge);
        return fieldArrayList;
    }

}
