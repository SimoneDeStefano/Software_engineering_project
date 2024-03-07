package it.polito.ezshop.dao;

import it.polito.ezshop.model.ReturnTransaction;

public class ReturnTransactionDao extends BaseAbstractDao<ReturnTransaction, Integer>{

    private static ReturnTransactionDao instance = null;
    
    private ReturnTransactionDao(){

    }

    public static ReturnTransactionDao getInstance(){
        if (instance == null) {
            instance = new ReturnTransactionDao();
        }
        return instance;
    }
}
