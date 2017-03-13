package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Offrande extends Cotisation {
    private StringProperty type;

    public Offrande(String type, int membre, int montant, LocalDate sabbat) {
        super(membre, montant, sabbat);
        this.type = new SimpleStringProperty(type);
    }

    @Override
    public String describe() {
        return super.describe();
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
