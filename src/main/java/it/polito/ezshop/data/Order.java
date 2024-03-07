package it.polito.ezshop.data;

import java.io.Serializable;

public interface Order extends Serializable{

    Integer getBalanceId();

    void setBalanceId(Integer balanceId);

    String getProductCode();

    void setProductCode(String productCode);

    double getPricePerUnit();

    void setPricePerUnit(double pricePerUnit);

    int getQuantity();

    void setQuantity(int quantity);

    String getStatus();

    void setStatus(String status);

    Integer getOrderId();

    void setOrderId(Integer orderId);
}
