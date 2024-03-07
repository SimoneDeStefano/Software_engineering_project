package it.polito.ezshop.dao;

import it.polito.ezshop.model.Order;

public class OrderDao extends BaseAbstractDao<Order, Integer> {

	private static OrderDao instance = null;

	private OrderDao() {

	}

	public static OrderDao getInstance() {
		if (instance == null) {
			instance = new OrderDao();
		}
		return instance;
	}

}
