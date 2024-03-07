package it.polito.ezshop.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.polito.ezshop.model.SaleTransaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.util.HibernateConfiguration;

public abstract class BaseAbstractDao<T, K> implements IBaseDao<T, K> {

	protected Session session;

	private final String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0]).getTypeName();

	public List<T> findAll() {

		Session session = HibernateConfiguration.getSessionFactory().openSession();

		List<T> result = session.createQuery("from " + className).list();

		session.close();

		return result;

	}

	public T findById(Object id) {

		Session session = HibernateConfiguration.getSessionFactory().openSession();

		T result = (T) session.get(className, (Serializable) id);

		session.close();

		return result;

	}

	public boolean delete(T entity) {

		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			session.delete(entity);
			session.close();
		} catch (NullPointerException e) {
			return false;
		} catch (Exception e) {
			session.close();

		}

		return true;
	}

	public T save(T entity) {
		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			session.saveOrUpdate(entity);
			session.close();

			return entity;

		} catch (NullPointerException e) {
		} catch (Exception e) {
			session.close();

		}

		return null;
	}

	public T appendSave(T entity, Session session) {

		try {
			session.saveOrUpdate(entity);
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

		return entity;

	}

	public List<T> findByCriteria(Map<String, Object> criteria) {

		try {

			if (criteria != null && !criteria.isEmpty()) {

				session = HibernateConfiguration.getSessionFactory().openSession();

				Criteria whereClause = session.createCriteria(className);
				Iterator it = criteria.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();

					whereClause.add(Restrictions.eq(pair.getKey().toString(), pair.getValue()));
				}

				List<T> result = whereClause.list();

				session.close();

				return result;
			}
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

		return null;
	}

	public List<T> findByCriteria(Map<String, Object> criteria, Session session) {

		try {

			if (criteria != null && !criteria.isEmpty()) {

				if (session == null) {
					session = HibernateConfiguration.getSessionFactory().openSession();
				}

				Criteria whereClause = session.createCriteria(className);
				Iterator it = criteria.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();

					whereClause.add(Restrictions.eq(pair.getKey().toString(), pair.getValue()));
				}

				List<T> result = whereClause.list();

				session.close();

				return result;
			}
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

		return null;
	}

	List<T> findByCriteria(String sql) {
		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			List<T> result = session.createQuery(sql).list();
			session.close();

			return result;

		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}
	}

	public void deleteAll() {

		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			session.createQuery("delete from " + className);

		} catch (NullPointerException e) {

		} catch (Exception e) {
			session.close();
		}
	}
}
