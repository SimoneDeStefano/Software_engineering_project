package it.polito.ezshop.data;

import it.polito.ezshop.model.ReturnedProduct;

import java.util.List;

public interface ReturnTransaction {

    Integer getId();

    void setId(Integer id);

    Integer getTicketNumber();

    void setTicketNumber(Integer saleTransactionNumber);

    String getStatus();

    void setStatus(String status);

    List<ReturnedProduct> getProducts();

    void setProducts(List<ReturnedProduct> products);

    void addProduct(ReturnedProduct product);

    double getPrice();

    void setPrice(double price);

}
