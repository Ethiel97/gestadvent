package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Dell on 21/09/2016.
 */
public class Bapteme {
    private ObjectProperty<LocalDate> dateBapteme;
    private StringProperty lieuBapteme;
    private StringProperty pasteur;
    private StringProperty egliseDest;
    private StringProperty eglisePro;
    private ObjectProperty<LocalDate> dateTransfert;

   /* Bapteme(ObjectProperty<LocalDate> dateTransfert) {
        this.dateTransfert = dateTransfert;
        this.dateBapteme = new SimpleObjectProperty<LocalDate>();
        this.lieuBapteme = new SimpleStringProperty("");
        this.pasteur = new SimpleStringProperty("");
    }*/

    public Bapteme() {
        this.dateBapteme = new SimpleObjectProperty<>();
        this.lieuBapteme = new SimpleStringProperty();
        this.pasteur = new SimpleStringProperty();
        this.egliseDest = new SimpleStringProperty();
        this.eglisePro = new SimpleStringProperty();
        this.dateTransfert = new SimpleObjectProperty<>(LocalDate.of(2000, 12, 8));
    }

    public Bapteme(LocalDate dateBapteme, String lieu, String pasteur, String egliseDest, String eglisePro, LocalDate dateTransfert) {
        this.dateBapteme = new SimpleObjectProperty<>(dateBapteme);
        this.lieuBapteme = new SimpleStringProperty(lieu);
        this.pasteur = new SimpleStringProperty(pasteur);
        this.egliseDest = new SimpleStringProperty(egliseDest);
        this.eglisePro = new SimpleStringProperty(eglisePro);
        this.dateTransfert = new SimpleObjectProperty<>(dateTransfert);
    }

    public LocalDate getDateBapteme() {
        return dateBapteme.get();
    }

    public void setDateBapteme(LocalDate LocalDate) {
        this.dateBapteme.set(LocalDate);
    }

    public ObjectProperty<LocalDate> dateBaptemeProperty() {
        return dateBapteme;
    }

    public String getLieuBapteme() {
        return lieuBapteme.get();
    }

    public void setLieuBapteme(String lieuBapteme) {
        this.lieuBapteme.set(lieuBapteme);
    }

    public StringProperty lieuBaptemeProperty() {
        return lieuBapteme;
    }

    public String getPasteur() {
        return pasteur.get();
    }

    public void setPasteur(String pasteur) {
        this.lieuBapteme.set(pasteur);
    }

    public StringProperty pasteurProperty() {
        return pasteur;
    }

    public String getEglisePro() {
        return eglisePro.get();
    }

    public StringProperty egliseProProperty() {
        return eglisePro;
    }

    public void setEglisePro(String eglisePro) {
        this.eglisePro.set(eglisePro);
    }

    public String getEgliseDest() {
        return egliseDest.get();
    }

    public StringProperty egliseDestProperty() {
        return egliseDest;
    }

    public void setEgliseDest(String egliseDest) {
        this.egliseDest.set(egliseDest);
    }

    public LocalDate getDateTransfert() {
        return dateTransfert.get();
    }

    public ObjectProperty<LocalDate> dateTransfertProperty() {
        return dateTransfert;
    }

    public void setDateTransfert(LocalDate dateTransfert) {
        this.dateTransfert.set(dateTransfert);
    }
}
