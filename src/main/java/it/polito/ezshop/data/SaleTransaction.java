package it.polito.ezshop.data;

import java.util.List;

public interface SaleTransaction {

    Integer getTicketNumber();

    void setTicketNumber(Integer ticketNumber);

    List<TicketEntry> getEntries();

    void setEntries(List<TicketEntry> entries);

    List<it.polito.ezshop.model.TicketEntry> getTicketEntries();

    void setTicketEntries(List<it.polito.ezshop.model.TicketEntry> ticketEntries);

    double getDiscountRate();

    void setDiscountRate(double discountRate);

    double getPrice();

    void setPrice(double price);

    String getStatus();

    void setStatus(String status);


}

