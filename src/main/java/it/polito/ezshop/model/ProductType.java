package it.polito.ezshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "product_type")
public class ProductType implements it.polito.ezshop.data.ProductType {

	private Integer quantity;
	private String location;
	private String note;
	private String productDescription;
	private String barCode;
	private Double pricePerUnit;
	private Integer id;

	public ProductType() {

	}

	public ProductType(Integer quantity, String location, String note, String productDescription, String barCode, Double pricePerUnit, Integer id) {
		this.quantity = quantity;
		this.location = location;
		this.note = note;
		this.productDescription = productDescription;
		this.barCode = barCode;
		this.pricePerUnit = pricePerUnit;
		this.id = id;
	}

	@Override
	@Column(name = "quantity")
	@ColumnDefault(value = "0")
	public Integer getQuantity() {
		
		return this.quantity;
	}

	@Override
	public void setQuantity(Integer quantity) {
		
		this.quantity = quantity;
	}

	@Override
	@Column(name = "location",unique = true)
	public String getLocation() {
		
		return this.location;
	}

	@Override
	public void setLocation(String location) {
		
		this.location = location;
	}

	@Override
	@Column(name = "note")
	public String getNote() {
		
		return this.note;
	}

	@Override
	public void setNote(String note) {
		
		this.note = note;
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
	@Column(name = "barcode",unique = true)
	public String getBarCode() {
		
		return this.barCode;
	}

	@Override
	public void setBarCode(String barCode) {
		
		this.barCode = barCode;
	}

	@Override
	@Column(name = "price_per_unit")
	public Double getPricePerUnit() {
		
		return this.pricePerUnit;
	}

	@Override
	public void setPricePerUnit(Double pricePerUnit) {
		
		this.pricePerUnit = pricePerUnit;
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

}
