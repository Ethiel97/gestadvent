package model;

import javafx.collections.ObservableList;

import java.sql.Connection;

/**
 * Created by Dell on 21/09/2016.
 */
public abstract class DAO<M, B> {

    protected static Connection con = null;

    public DAO(Connection con) {
        DAO.con = con;
    }

    public abstract int add(M m, B b);

    public abstract int delete(M m);

    public abstract int update(M m, B b);

    public abstract Object[] getUnique(M m, B b);

    public abstract ObservableList<M> getList(String status);

    public abstract int changeStatus(M m,String status);

}
