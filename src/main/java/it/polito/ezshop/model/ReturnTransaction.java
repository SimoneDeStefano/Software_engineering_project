package it.polito.ezshop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "returnTransactions")
public class ReturnTransaction implements it.polito.ezshop.data.ReturnTransaction{

    private Integer id;
    private Integer ticketNumber;
    private String status;
    private List<ReturnedProduct> products = new ArrayList<>(0);
    private double price;

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
    @Column(name = "TicketNumber")
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    @OneToMany(mappedBy = "returnTransaction", fetch = FetchType.EAGER)
    public List<ReturnedProduct> getProducts(){
        return this.products;
    }

    @Override
    public void setProducts(List<ReturnedProduct> products) {
        this.products = products;
    }

    @Override
    public void addProduct(ReturnedProduct product) {
        this.products.add(product);
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }
}
