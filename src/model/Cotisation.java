package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Created by Ethiel on 07/03/2017.
 */
public abstract class Cotisation {

    //    protected StringProperty type;
    protected IntegerProperty montant;
    protected ObjectProperty<LocalDate> sabbat;

    protected IntegerProperty membre;

    private static String COTISATION = "Cotisation";


    Cotisation(int membre, int montant, LocalDate sabbat) {
        this.membre = new SimpleIntegerProperty(membre);
        this.montant = new SimpleIntegerProperty(montant);
        this.sabbat = new SimpleObjectProperty<>(sabbat);

    }

    public int getMontant() {
        return montant.get();
    }

    public IntegerProperty montantProperty() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant.set(montant);
    }

    public ObjectProperty<LocalDate> sabbatProperty() {
        return sabbat;
    }

    public void setSabbat(LocalDate sabbat) {
        this.sabbat.set(sabbat);
    }

    public LocalDate getSabbat() {
        return sabbat.get();
    }

    public String describe() {
//        return label != null ? label + " du Sabbat " + this.sabbat + " :" + this.montant : COTISATION + " du Sabbat " + this.sabbat + " :" + this.montant;
        return /*this.type + */" du Sabbat " + this.sabbat + ": " + this.montant;
    }

    public int getMembre() {
        return membre.get();
    }

    public IntegerProperty membreProperty() {
        return membre;
    }

    public void setMembre(int membre) {
        this.membre.set(membre);
    }


}
