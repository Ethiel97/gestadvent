package controller;/**
 * Created by Ethiel on 21/11/2016.
 */

import DB.DBUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bapteme;
import model.Membre;
import model.MembreBDAO;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class ConsultController implements Initializable {
    private static final Logger logger = Logger.getLogger(ConsultController.class.getName());

    @FXML
    private TextField searchField;
    @FXML
    private Button exportExcelButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<Membre, Integer> idColumn;
    @FXML
    private TableColumn<Membre, String> emailColumn;
    @FXML
    private TableColumn<Membre, LocalDate> situationMColumn;
    @FXML
    private TableView<Membre> tableViewMember;
    @FXML
    private TableColumn<Membre, String> nomColumn;
    @FXML
    private TableColumn<Membre, Integer> ageColumn;
    @FXML
    private TableColumn<Membre, String> prenomColumn;
    @FXML
    private TableColumn<Membre, String> sexeColumn;
    @FXML
    private TableColumn<Membre, String> professionColumn;
    @FXML
    private TableColumn<Membre, String> adresseColumn;
    @FXML
    private TableColumn<Membre, LocalDate> naissanceColumn;
    @FXML
    private TableColumn<Membre, String> telephoneColumn;
    @FXML
    private Button printButton;
    @FXML
    private Button refreshButton;
    @FXML
    private ComboBox<String> criteriaComboBox;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView backHome;

    private FilteredList<Membre> filteredList;
    private SortedList<Membre> sortedList;


    private MembreBDAO membreBDAO;
    private Connection connection;
    private ObservableList<String> criteriaList = FXCollections.observableArrayList("Id", "Nom", "Sexe", "Email", "Profession");
    private String status = "active";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criteriaComboBox.getItems().setAll(criteriaList);
        criteriaComboBox.getSelectionModel().select(0);
        Tooltip t = new Tooltip("Cliquez ici pour retourner au Dashboard");
        Tooltip.install(backHome, t);
        Tooltip t1 = new Tooltip("Imprimer");
        Tooltip.install(printButton, t1);
        Tooltip t2 = new Tooltip("Rafraichir la liste");
        Tooltip.install(refreshButton, t2);
        Tooltip t3 = new Tooltip("Rechercher");
        Tooltip.install(searchButton, t3);
        Tooltip t4 = new Tooltip("Exporter vers Excel");
        Tooltip.install(exportExcelButton, t4);
        backHome.setCursor(Cursor.HAND);
        DashboardController.fadeTransition(anchorPane);
/*
        ImageView printImage = new ImageView(new Image(getClass().getResource("../images/print.png").toExternalForm()));
        ImageView refreshImage = new ImageView(new Image(getClass().getResource("../images/refresh.png").toExternalForm()));
        printButton.setGraphic(printImage);
        refreshButton.setGraphic(refreshImage);
        idColumn.setCellValueFactory (new PropertyValueFactory<Membre, Integer> ("id"));
        nomColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("nom"));
        prenomColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("prenom"));
        sexeColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("sexe"));
        naissanceColumn.setCellValueFactory (new PropertyValueFactory<Membre, LocalDate> ("dateNaissance"));
        situationMColumn.setCellValueFactory (new PropertyValueFactory<Membre, LocalDate> ("situationM"));
        emailColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("email"));
        professionColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("profession"));
        telephoneColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("telephone"));
        adresseColumn.setCellValueFactory (new PropertyValueFactory<Membre, String> ("adresse"));
        tableViewMember.getSelectionModel ().setSelectionMode (SelectionMode.SINGLE);*/
        /*  connection = DBUtil.getConnexion ();
          membreBDAO = new MembreBDAO (connection);
          ObservableList<Membre> membreData = membreBDAO.getList ();
          tableViewMember.setItems (membreData);*/
        populateTableView();
        searchFilterTableView();
       /* filteredList = new FilteredList<> (membreBDAO.getList (status), p -> true);
        searchField.textProperty ().addListener ((observable, oldValue, newValue) -> {
            filteredList.setPredicate (membre -> {
                if (newValue == null || newValue.isEmpty ()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase ();
                if (membre.getNom ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getPrenom ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getSexe ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getProfession ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getSituationM ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getTelephone ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getDateNaissance ().toString ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            sortedList = new SortedList<> (filteredList);
            sortedList.comparatorProperty ().bind (tableViewMember.comparatorProperty ());
            tableViewMember.setItems (sortedList);
        });*/

        tableViewMember.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Membre>() {
            @Override
            public void changed(ObservableValue<? extends Membre> observable, Membre oldValue, Membre newValue) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/membreDetails.fxml"));
                try {
                    Parent parent = loader.load();
                    MembreDetailsController membreDetailsController = loader.getController();
                    Bapteme bapteme = new Bapteme();
                    connection = DBUtil.getConnexion();
                    System.out.println(newValue.getId() + " l'id en question");
                    membreBDAO = new MembreBDAO(connection);
                    Object[] data = membreBDAO.getUnique(newValue, bapteme);
                    membreDetailsController.initData((Membre) data[0], (Bapteme) data[1]);
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(anchorPane.getScene().getWindow());
                    stage.setScene(scene);
                    stage.setResizable(false);
//                    stage.initStyle (StageStyle.UNDECORATED);
                    stage.show();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void backHome(MouseEvent mouseEvent) {
        Parent root = null;
        Scene scene;
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void refreshTableView(ActionEvent actionEvent) {

        logger.info("Rafraichissement de la liste");
        tableViewMember.getItems().clear();
        try {
            connection = DBUtil.getConnexion();
            membreBDAO = new MembreBDAO(connection);
            String criteria;

            if (criteriaComboBox.getSelectionModel().getSelectedItem() != null) {
                criteria = criteriaComboBox.getSelectionModel().getSelectedItem();
            } else {
                criteria = "Nom";
            }

            ObservableList<Membre> membreData = membreBDAO.getMembreListWithCriteria(status, criteria);
           /* filteredList = new FilteredList<>(membreBDAO.getList(status), p -> true);
            filteredList = new FilteredList<>(membreData, p -> true);
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableViewMember.comparatorProperty());
            tableViewMember.setItems(sortedList);*/
            tableViewMember.setItems(membreData);
            System.out.println(criteria + " le critere en question");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void printList(ActionEvent event) {

        String query = "SELECT * FROM membre JOIN bapteme ON membre.id = bapteme.membre";
        try {
            connection = DBUtil.getConnexion();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.penThin()).setBackgroundColor(new Color(35, 155, 45));

        StyleBuilder boldCenteredStyleTitle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(23).setForegroundColor(Color.black);

        JasperReportBuilder reportBuilder = DynamicReports.report();
        reportBuilder.columns(
                Columns.column("Nom", "nom", DataTypes.stringType()),
                Columns.column("Prenoms", "prenom", DataTypes.stringType()),
                Columns.column("sexe", "sexe", DataTypes.stringType()),
                Columns.column("Date de Naissance", "date_N", DataTypes.dateType()),
                Columns.column("Lieu de Naissance", "lieu_N", DataTypes.stringType()),
                Columns.column("Telephone", "phone", DataTypes.stringType()),
                Columns.column("Profession", "profession", DataTypes.stringType()),
                Columns.column("Situtation Matrimoniale", "situation_M", DataTypes.stringType()),
                Columns.column("Email", "Email", DataTypes.stringType()),
                Columns.column("Adresse", "adresse", DataTypes.stringType()),
                Columns.column("Date de bapteme", "date_b", DataTypes.dateType()),
                Columns.column("Lieu de bapteme", "lieu_b", DataTypes.stringType()),
                Columns.column("Pasteur Officiant", "pasteur", DataTypes.stringType())
        ).setColumnTitleStyle(
                columnTitleStyle
        ).highlightDetailEvenRows()
                .title(cmp.text("Liste des Membres").setStyle(boldCenteredStyleTitle))
                .pageFooter(cmp.pageXslashY().setStyle(boldCenteredStyle))
                /*.background (cmp.image (getClass ().getResourceAsStream ("../images/logo.png")).setFixedDimension (350, 350)
                        .setStyle (Styles.style ().setHorizontalImageAlignment (HorizontalImageAlignment.CENTER)))*/
                .setDataSource(query, connection).setPageFormat(PageType.B3, PageOrientation.PORTRAIT);

        try {
            reportBuilder.show(false);
//            reportBuilder.toPdf (new FileOutputStream ("../liste.pdf"));
            reportBuilder.toPdf(new FileOutputStream("c:/report.pdf"));
        } catch (DRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Membre, Integer>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("prenom"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("sexe"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Membre, Integer>("age"));
        naissanceColumn.setCellValueFactory(new PropertyValueFactory<Membre, LocalDate>("dateNaissance"));
        situationMColumn.setCellValueFactory(new PropertyValueFactory<Membre, LocalDate>("situationM"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("email"));
        professionColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("profession"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("telephone"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<Membre, String>("adresse"));
        tableViewMember.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            connection = DBUtil.getConnexion();
            membreBDAO = new MembreBDAO(connection);
            ObservableList<Membre> membreData = membreBDAO.getList(status);
            tableViewMember.setItems(membreData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchFilterTableView() {
        filteredList = new FilteredList<>(membreBDAO.getList(status), p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(membre -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (membre.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getSexe().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(membre.getAge()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getProfession().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getSituationM().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getTelephone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (membre.getDateNaissance().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableViewMember.comparatorProperty());
            tableViewMember.setItems(sortedList);
        });
    }
}