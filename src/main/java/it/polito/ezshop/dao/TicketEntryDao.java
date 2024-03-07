package it.polito.ezshop.dao;

import it.polito.ezshop.model.TicketEntry;

public class TicketEntryDao extends BaseAbstractDao<TicketEntry, Integer> {

	private static TicketEntryDao instance = null;

	private TicketEntryDao() {

	}

	public static TicketEntryDao getInstance() {
		if (instance == null) {
			instance = new TicketEntryDao();
		}
		return instance;
	}

}
