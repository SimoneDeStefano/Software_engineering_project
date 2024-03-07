package it.polito.ezshop.data;

import it.polito.ezshop.model.ReturnTransaction;

public interface ReturnedProduct {

    Integer getId();

    void setId(Integer id);

    String getBarCode();

    void setBarCode(String barCode);

    ReturnTransaction getReturnTransaction();

    void setReturnTransaction(ReturnTransaction returnTransaction);

    Integer getAmount();

    void setAmount(Integer amount);

    double getPrice();

    void setPrice(double price);

}
