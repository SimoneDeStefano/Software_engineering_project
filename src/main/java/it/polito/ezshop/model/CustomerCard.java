package it.polito.ezshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_card")
public class CustomerCard implements it.polito.ezshop.data.CustomerCard {

    private String cardCode;
    private Integer cardPoints;
    private Integer id;
    private Integer customerId;

    @Override
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    @Column(name = "customerId")
    public Integer getCustomerId() {
        return this.customerId;
    }

    @Override
    public void setCustomerId(Integer id) {
        this.customerId = id;
    }

    @Override
    @Column(name = "cardCode")
    public String getCardCode() {
        return this.cardCode;
    }

    @Override
    public void setCardCode(String code) {
        this.cardCode = code;
    }

    @Override
    @Column(name = "cardPoints")
    public Integer getCardPoints() {
        return this.cardPoints;
    }

    @Override
    public void setCardPoints(Integer points) {
        this.cardPoints = points;
    }


}
