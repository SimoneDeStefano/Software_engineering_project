package it.polito.ezshop.dao;

import it.polito.ezshop.model.Customer;

public class CustomerDao extends BaseAbstractDao<Customer, Integer> {

	private static CustomerDao instance = null;

	private CustomerDao() {

	}

	public static CustomerDao getInstance() {
		if (instance == null) {
			instance = new CustomerDao();
		}
		return instance;
	}

}
