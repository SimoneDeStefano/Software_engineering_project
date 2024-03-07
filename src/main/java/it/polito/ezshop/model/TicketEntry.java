package it.polito.ezshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_entry")
public class TicketEntry implements it.polito.ezshop.data.TicketEntry {


	private String barCode;
	private String productDescription;
	private int amount;
	private double pricePerUnit;
	private double discountRate;
	private Integer ticket_number;

	@Column(name = "ticket_number")
	public Integer getTicket_number() {
		return ticket_number;
	}

	public void setTicket_number(Integer ticket_number) {
		this.ticket_number = ticket_number;
	}



	@Override
	@Id
	@Column(name = "barcode")
	public String getBarCode() {
		
		return this.barCode;
	}

	@Override
	public void setBarCode(String barCode) {
		
		this.barCode = barCode;
	}

	@Override
	@Column(name = "product_description")
	public String getProductDescription() {
		
		return this.productDescription;
	}

	@Override
	public void setProductDescription(String productDescription) {
		
		this.productDescription = productDescription;
	}

	@Override
	@Column(name = "amount")
	public int getAmount() {
		
		return this.amount;
	}

	@Override
	public void setAmount(int amount) {
		
		this.amount = amount;
	}

	@Override
	@Column(name = "pricePerUnit")
	public double getPricePerUnit() {
		
		return pricePerUnit;
	}

	@Override
	public void setPricePerUnit(double pricePerUnit) {
		
		this.pricePerUnit = pricePerUnit;
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

}
