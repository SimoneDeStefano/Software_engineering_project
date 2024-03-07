package it.polito.ezshop.dao;

import java.util.HashMap;
import java.util.List;

import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.util.HibernateConfiguration;
import org.hibernate.Session;

public class ProductTypeDao extends BaseAbstractDao<ProductType, Integer> {

	private static ProductTypeDao instance = null;

	private ProductTypeDao() {

	}

	public static ProductTypeDao getInstance() {
		if (instance == null) {
			instance = new ProductTypeDao();
		}
		return instance;
	}

	public ProductType findByBarCode(String barcode) {

		List<ProductType> result = findByCriteria(new HashMap<String, Object>() {
			{
				put("barCode", barcode);
			}
		});

		if (result != null && !result.isEmpty()) {

			return result.get(0);

		}

		return null;
	}

	public ProductType findByBarCode(String barcode, Session session) {

		List<ProductType> result = findByCriteria(new HashMap<String, Object>() {
			{
				put("barCode", barcode);
			}
		},session);

		if (result != null && !result.isEmpty()) {

			return result.get(0);

		}

		return null;
	}


	public List<ProductType> findByDescription(String description) {

		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			List<ProductType> result = session
					.createQuery("from ProductType where productDescription like :description")
					.setString("description", description + "%").list();
			session.close();
			return result;
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

	}

}
