package com.manalo.prototype.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Common Hibernate implementations
 */
public abstract class Dao<T> {
	
	@Inject
	private SessionFactory sessionFactory;
	
	/**
	 * Returns current Hibernate session
	 * 
	 * @return {@link Session}
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Persists the T instance and returns a serialisable object
	 * 
	 * @param object
	 *            the Object to save
	 * @return serialisable object
	 */
	public Integer save(final T object) {
		return (Integer) getSession().save(object);
	}
	
	/**
	 * Persists the T instance
	 * 
	 * @param object
	 *            the Object to save
	 */
	public void persist(final T object) {
		getSession().persist(object);
	}
	
	/**
	 * Creates a new record if T is transient and updates T if it's persistent
	 * 
	 * @param object
	 *            the Object to save or update
	 */
	public void saveOrUpdate(final T object) {
		getSession().saveOrUpdate(object);
	}
	
	/**
	 * Updates the T persistent instance
	 * 
	 * @param object
	 *            the Object to update
	 */
	public void update(final T object) {
		getSession().update(object);
	}
	
	/**
	 * Copies the state of the T instance into the persistent Object
	 * 
	 * @param object
	 *            the Object to copy
	 * @return the instance of T
	 */
	@SuppressWarnings("unchecked")
	public T merge(T object) {
		return (T) getSession().merge(object);
	}
	
	/**
	 * Deletes the T persistent instance
	 * 
	 * @param object
	 *            the Object to delete
	 */
	public void delete(T object) {
		getSession().delete(object);
	}
	
	/**
	 * Returns the T instance of the matching id
	 * 
	 * @param id
	 *            the id to search for
	 * @param clazz
	 *            the T class to search from
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(Integer id, Class<T> clazz) {
		
		String hql = "from " + clazz.getCanonicalName() + " where id = :id";
		
		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		
		return (T) query.uniqueResult();
	}
	
	/**
	 * Returns a List of the T instances
	 * 
	 * @param clazz
	 *            the T class to be listed
	 * @return {@link List}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getList(Class<T> clazz) {
		
		Query query = getSession().createQuery("from " + clazz.getCanonicalName());
		return query.list();
	}
	
}
