package it.polito.ezshop.dao;

import java.util.HashMap;
import java.util.List;

public class UserDao extends BaseAbstractDao<it.polito.ezshop.model.User, Integer> {

	private static UserDao instance = null;

	private UserDao() {

	}

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	public it.polito.ezshop.model.User findByUsername(String username) {

		List<it.polito.ezshop.model.User> result = findByCriteria(new HashMap<String, Object>() {
			{
				put("username", username);
			}
		});

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

}
