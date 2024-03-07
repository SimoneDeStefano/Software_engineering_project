package it.polito.ezshop.dao;

import it.polito.ezshop.model.ReturnedProduct;

public class ReturnedProductDao extends BaseAbstractDao<ReturnedProduct, Integer>{

    private static ReturnedProductDao instance = null;

    private ReturnedProductDao(){

    }

    public static ReturnedProductDao getInstance(){
        if (instance == null) {
            instance = new ReturnedProductDao();
        }
        return instance;
    }
}
