package model;

import java.time.LocalDate;

/**
 * Created by Ethiel on 08/03/2017.
 */
public class Dime extends Cotisation {
    public Dime(int membre, int montant, LocalDate sabbat) {

        super(membre, montant, sabbat);
    }

    @Override
    public String describe() {
        return super.describe();
    }
}
