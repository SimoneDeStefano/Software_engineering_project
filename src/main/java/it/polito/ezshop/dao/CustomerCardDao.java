package it.polito.ezshop.dao;

import it.polito.ezshop.model.CustomerCard;

public class CustomerCardDao extends BaseAbstractDao<CustomerCard, Integer> {

    private static CustomerCardDao instance = null;

    private CustomerCardDao() {

    }

    public static CustomerCardDao getInstance() {
        if (instance == null) {
            instance = new CustomerCardDao();
        }
        return instance;
    }

}