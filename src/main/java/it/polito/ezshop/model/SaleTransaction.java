package it.polito.ezshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import it.polito.ezshop.model.TicketEntry;


@Entity
@Table(name = "sale_transaction")
public class SaleTransaction implements it.polito.ezshop.data.SaleTransaction {


	private Integer ticketNumber;

	//private List<it.polito.ezshop.model.TicketEntry> ticketEntries = new ArrayList<it.polito.ezshop.model.TicketEntry>(
			//0);
	private double discountRate;
	private double price;
	private List<TicketEntry> ticketEntries = new ArrayList<>(0);
	private String status;

	@Override
	@Id
	@Column(name = "ticket_number")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getTicketNumber() {
		return this.ticketNumber;
	}

	@Override
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}


	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ticket_number")
	public List<it.polito.ezshop.model.TicketEntry> getTicketEntries() {
		return this.ticketEntries;
	}


	public void setTicketEntries(List<it.polito.ezshop.model.TicketEntry> ticketEntries) {
		this.ticketEntries = ticketEntries;
	}

	@Override
	@Column(name = "discount_rate")
	public double getDiscountRate() {

		return this.discountRate;
	}

	@Override
	public void setDiscountRate(double discountRate) {

		this.discountRate = discountRate;
	}

	@Override
	@Column(name = "price")
	public double getPrice() {

		return this.price;
	}

	@Override
	public void setPrice(double price) {

		this.price = price;
	}

	@Transient
	public List<it.polito.ezshop.data.TicketEntry> getEntries() {

		return this.getTicketEntries().stream().map(ticketEntry -> {
			it.polito.ezshop.data.TicketEntry entri = ticketEntry;
			return entri;
		}).collect(Collectors.toList());
	}

	@Override
	public void setEntries(List<it.polito.ezshop.data.TicketEntry> entries) {
	}

	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}



}
