package it.polito.ezshop.dao;

import it.polito.ezshop.model.SaleTransaction;

public class SaleTransactionDao extends BaseAbstractDao<SaleTransaction, Integer> {

	private static SaleTransactionDao instance = null;

	private SaleTransactionDao() {

	}

	public static SaleTransactionDao getInstance() {
		if (instance == null) {
			instance = new SaleTransactionDao();
		}
		return instance;
	}

}
