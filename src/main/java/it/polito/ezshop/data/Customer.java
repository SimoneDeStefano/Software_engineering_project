package it.polito.ezshop.data;

import it.polito.ezshop.model.CustomerCard;

public interface Customer {

    String getCustomerName();

    void setCustomerName(String customerName);

    String getCustomerCard();

    void setCustomerCard(String customerCard);

    Integer getId();

    void setId(Integer id);

    Integer getPoints();

    void setPoints(Integer points);
}
