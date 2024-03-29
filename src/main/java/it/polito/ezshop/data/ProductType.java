package it.polito.ezshop.data;

import java.io.Serializable;

public interface ProductType extends Serializable{

    Integer getQuantity();

    void setQuantity(Integer quantity);

    String getLocation();

    void setLocation(String location);

    String getNote();

    void setNote(String note);

    String getProductDescription();

    void setProductDescription(String productDescription);

    String getBarCode();

    void setBarCode(String barCode);

    Double getPricePerUnit();

    void setPricePerUnit(Double pricePerUnit);

    Integer getId();

    void setId(Integer id);

}
