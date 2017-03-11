package model;

import javafx.collections.ObservableList;

/**
 * Created by Ethiel on 10/03/2017.
 */
public interface DaoCo<D>{

    int add(D d);

    void delete(D d);

    void update(D d);

    ObservableList getAll();

    Object getUnique(D d);
}
