package model;

import java.time.LocalDate;

/**
 * Created by Ethiel on 08/03/2017.
 */
public class Offrande extends Cotisation {
    Offrande(String type,double montant, LocalDate sabbat) {
        super(type,montant, sabbat);
    }

    @Override
    public String describe() {
        return super.describe();
    }
}
