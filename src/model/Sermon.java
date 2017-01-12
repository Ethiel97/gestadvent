package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Ethiel on 09/01/2017.
 */
public class Sermon {

    private StringProperty predicateur;
    private StringProperty titre;
    private ObjectProperty<LocalDate> datePredication;


    public Sermon(String predicateur, String titre, LocalDate datePredication) {
        this.predicateur = new SimpleStringProperty (predicateur);
        this.titre = new SimpleStringProperty (titre);
        this.datePredication = new SimpleObjectProperty<> (datePredication);
    }

    public String getPredicateur() {
        return predicateur.get ();
    }

    public StringProperty predicateurProperty() {
        return predicateur;
    }

    public void setPredicateur(String predicateur) {
        this.predicateur.set (predicateur);
    }

    public String getTitre() {
        return titre.get ();
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set (titre);
    }

    public LocalDate getDatePredication() {
        return datePredication.get ();
    }

    public ObjectProperty<LocalDate> datePredicationProperty() {
        return datePredication;
    }

    public void setDatePredication(LocalDate datePredication) {
        this.datePredication.set (datePredication);
    }
}
