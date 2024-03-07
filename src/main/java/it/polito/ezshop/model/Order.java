package it.polito.ezshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_shop")
public class Order implements it.polito.ezshop.data.Order {

	private Integer balanceId;
	private String productCode;
	private ProductType productType;
	private Double pricePerUnit;
	private Integer quantity;
	private String status;
	private Integer orderId;
	
	@OneToOne
	@JoinColumn(name = "product_code", referencedColumnName = "barCode",insertable = false,updatable = false)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	
	@Override
	@Column(name = "balance_id")
	public Integer getBalanceId() {
		
		return this.balanceId;
	}

	@Override
	public void setBalanceId(Integer balanceId) {
		
		this.balanceId = balanceId;
	}

	@Override
	@Column(name = "product_code")
	public String getProductCode() {
		
		return this.productCode;
	}

	@Override
	public void setProductCode(String productCode) {
		
		this.productCode = productCode;
	}

	@Override
	@Column(name = "price_per_unit")
	public double getPricePerUnit() {
		
		return this.pricePerUnit;
	}

	@Override
	public void setPricePerUnit(double pricePerUnit) {
		
		this.pricePerUnit = pricePerUnit;

	}

	@Override
	@Column(name = "quantity")
	public int getQuantity() {
		
		return this.quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		
		this.quantity = quantity;
	}

	@Override
	@Column(name = "status")
	public String getStatus() {
		
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		
		this.status = status;
	}

	@Override
	@Id
	@Column(name = "orderId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getOrderId() {
		
		return this.orderId;
	}

	@Override
	public void setOrderId(Integer orderId) {
		
		this.orderId = orderId;
	}

}
