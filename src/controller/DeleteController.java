package controller;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bapteme;
import model.Membre;
import model.MembreBDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Ethiel on 28/12/2016.
 */
public class DeleteController implements Initializable {

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
    private ComboBox criteriaComboBox;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView backHome;
    private FilteredList<Membre> filteredList;
    private SortedList<Membre> sortedList;


    private MembreBDAO membreBDAO;
    private Connection connection;
    private ObservableList<String> criteriaList = FXCollections.observableArrayList ("Id", "Nom", "Sexe", "Email", "Profession");
    private String status = "inactive";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DashboardController.fadeTransition (anchorPane);
        criteriaComboBox.getItems ().addAll (criteriaList);
        criteriaComboBox.getSelectionModel ().select (0);
        Tooltip t = new Tooltip ("Cliquez ici pour retourner au Dashboard");
        Tooltip.install (backHome, t);
        Tooltip t1 = new Tooltip ("Imprimer");
        Tooltip.install (printButton, t1);
        Tooltip t2 = new Tooltip ("Rafraichir la liste");
        Tooltip.install (refreshButton, t2);
        Tooltip t3 = new Tooltip ("Rechercher");
        Tooltip.install (searchButton, t3);
        Tooltip t4 = new Tooltip ("Exporter vers Excel");
        Tooltip.install (exportExcelButton, t4);
        backHome.setCursor (Cursor.HAND);

        searchButton.setOnAction (e -> {
            searchFilterTableView ();
        });
        populateTableView ();
        searchFilterTableView ();

        tableViewMember.getSelectionModel ().selectedItemProperty ().addListener (new ChangeListener<Membre> () {
            @Override
            public void changed(ObservableValue<? extends Membre> observable, Membre oldValue, Membre newValue) {
                FXMLLoader loader = new FXMLLoader (getClass ().getResource ("../view/membreInactiveDetails.fxml"));
                try {
//                    logger.info(newValue.getNom() + " " + newValue.getPrenom());
                    Parent parent = loader.load ();
                    MembreInactiveDetailsController membreInactiveDetailsController = loader.getController ();
                    Bapteme bapteme = new Bapteme ();
                    connection = DBUtil.getConnexion ();
                    membreBDAO = new MembreBDAO (connection);
                    Object[] data = membreBDAO.getUnique (newValue, bapteme);
                    membreInactiveDetailsController.initData ((Membre) data[0], (Bapteme) data[1]);
                    Stage stage = new Stage ();
                    Scene scene = new Scene (parent);
                    stage.initModality (Modality.APPLICATION_MODAL);
                    stage.initOwner (anchorPane.getScene ().getWindow ());
                    stage.setScene (scene);
                    stage.setResizable (false);
                    stage.show ();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace ();
                }
            }
        });
    }

    @FXML
    private void backHome(MouseEvent mouseEvent) {
        Scene scene;
        Parent root = null;
        Stage stage = (Stage)((ImageView)mouseEvent.getSource ()).getScene ().getWindow ();
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
    private void refreshTableView(ActionEvent event) {
        try {
            String status = "inactive";
            connection = DBUtil.getConnexion ();
            membreBDAO = new MembreBDAO (connection);
            String criteria;
            if (criteriaComboBox.getSelectionModel ().getSelectedItem ().toString () != null)
                criteria = criteriaComboBox.getSelectionModel ().getSelectedItem ().toString ();
            else
                criteria = "Nom";
            ObservableList<Membre> membreData = membreBDAO.getMembreListWithCriteria (status, criteria);
            filteredList = new FilteredList<> (membreBDAO.getList (status), p -> true);
            sortedList = new SortedList<> (filteredList);
            sortedList.comparatorProperty ().bind (tableViewMember.comparatorProperty ());
//            tableViewMember.setItems(sortedList);
            tableViewMember.setItems (membreData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }

    private void populateTableView() {
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
        tableViewMember.getSelectionModel ().setSelectionMode (SelectionMode.SINGLE);

        try {
            connection = DBUtil.getConnexion ();
            membreBDAO = new MembreBDAO (connection);
            ObservableList<Membre> membreData = membreBDAO.getList (status);
            tableViewMember.setItems (membreData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }

    private void searchFilterTableView() {
        filteredList = new FilteredList<> (membreBDAO.getList (status), p -> true);
        searchField.textProperty ().addListener ((observable, oldValue, newValue) -> {

            filteredList.setPredicate (membre -> {
                if (newValue == null || newValue.isEmpty ()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase ();
                if (membre.getNom ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getAdresse ().toLowerCase ().contains (lowerCaseFilter)) {
                    return true;
                } else if (membre.getEmail ().toLowerCase ().contains (lowerCaseFilter)) {
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
        });
    }
}
