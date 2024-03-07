package it.polito.ezshop.model;

import javax.persistence.*;


@Entity
@Table(name = "returnedProduct")
public class ReturnedProduct implements it.polito.ezshop.data.ReturnedProduct {

    private Integer returnedId;
    private String barCode;
    private  double price;
    private Integer amount;
    private ReturnTransaction returnTransaction;


    @Override
    @Id
    @Column(name = "returnedId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.returnedId;
    }

    @Override
    public void setId(Integer id) {
        this.returnedId = id;
    }

    @Override
    @Column(name = "barCode")
    public String getBarCode() {
        return this.barCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "id")
    public ReturnTransaction getReturnTransaction() {
        return this.returnTransaction;
    }

    @Override
    public void setReturnTransaction(ReturnTransaction returnTransaction) {
        this.returnTransaction = returnTransaction;
    }

    @Override
    @Column(name = "returnedAmount")
    public Integer getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(Integer amount){
        this.amount = amount;
    }

    @Override
    @Column(name = "price")
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price){
        this.price = price;
    }
}
