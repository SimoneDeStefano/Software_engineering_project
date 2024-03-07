package it.polito.ezshop.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "balance_operation")
public class BalanceOperation implements it.polito.ezshop.data.BalanceOperation {

	private int balanceId;
	private String created_date;
	private double money;
	private String type;

	@Override
	@Id
	@Column(name = "balance_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getBalanceId() {

		return this.balanceId;
	}

	@Override
	public void setBalanceId(int balanceId) {

		this.balanceId = balanceId;
	}

	@Override
	@Column(name = "money")
	public double getMoney() {

		return this.money;
	}

	@Override
	public void setMoney(double money) {

		this.money = money;
	}

	@Override
	@Column(name = "type")
	public String getType() {

		return this.type;
	}

	@Override
	public void setType(String type) {

		this.type = type;
	}

	@Column(name = "created_date")
	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	@Override
	@Transient
	public LocalDate getDate() {

		if (created_date != null) {

			return LocalDate.parse(created_date);
		}
		return null;
	}

	@Override
	public void setDate(LocalDate date) {

		this.created_date = date != null ? date.toString() : null;
	}

}
