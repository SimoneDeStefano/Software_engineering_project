package it.polito.ezshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements it.polito.ezshop.data.Customer {

	private String customerName;
	private String customerCard;
	private Integer id;
	private Integer points;
	
	@Override
	@Column(name = "customerName")
	public String getCustomerName() {
		return this.customerName;
	}

	@Override
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	@Column(name = "customerCard")
	public String getCustomerCard() {
		return this.customerCard;
	}

	@Override
	public void setCustomerCard(String customerCard) {
		this.customerCard = customerCard;
	}

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
	@Column(name = "points")
	public Integer getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(Integer points) {
		this.points = points;
	}

}
