package it.polito.ezshop.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T,K> {

	List<T> findAll();
	T save(T entity);
	boolean delete(T entity);
	List<T> findByCriteria(Map<String, Object> criteria);
	
	
}
