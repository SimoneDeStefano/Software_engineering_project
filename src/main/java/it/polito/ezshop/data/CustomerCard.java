package it.polito.ezshop.data;

public interface CustomerCard {

    Integer getId();

    void setId(Integer id);

    Integer getCustomerId();

    void setCustomerId(Integer id);

    String getCardCode();

    void setCardCode(String code);

    Integer getCardPoints();

    void setCardPoints(Integer points);

}
