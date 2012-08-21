package com.realdolmen.springjsf.integration;

import java.util.List;

/**
 * Interface for Repositories having common CRUD functionalities.
 * 
 * @param <T>
 *            a type variable
 * @param <K>
 *            the primary key for that type
 */
public interface GenericRepository<T, K> {
	
	/**
	 * Finds an entity by its id.
	 * 
	 * @param id
	 *            the id
	 * @return the entity or null if not found
	 */
	T findById(K id);

	/**
	 * Find by id with eager fetching of the properties listed in the
	 * properties.
	 * 
	 * @param id
	 *            the id
	 * @param properties
	 *            the properties
	 * 
	 * @return the t
	 */
	T findByIdEager(final K id, final String... properties);

	/**
	 * Gets all entities.
	 * 
	 * @return a list of all entities
	 */
	List<T> findAll();

	/**
	 * Gets all entities in the specified range.
	 * 
	 * @param first
	 *            first of range to retrieve
	 * @param max
	 *            max number of rows to retrieve
	 * @return a list of all entities
	 */
	List<T> findAll(int first, int max);

	/**
	 * Returns the total records of a findAll query.
	 * 
	 * @return the total records of a findAll query
	 */
	long countAll();

	/**
	 * Gets all entities, ordered by a specific field.
	 * 
	 * @param sorting
	 *            list of sorting
	 * @return a sorted list of all entities
	 */
	List<T> findAll(List<Sort> sorting);

	/**
	 * 
	 * 
	 * Gets all entities in the specified range, ordered by a specific field.
	 * 
	 * @param sorting
	 *            list of sort
	 * @param first
	 *            first of range to retrieve
	 * @param max
	 *            max number of rows to retrieve
	 * @return a sorted list of all entities
	 */
	List<T> findAll(List<Sort> sorting, int first, int max);

	/**
	 * Persists the specified entity.
	 * 
	 * @param entity
	 *            the entity to save
	 * @return a reference to the saved entity
	 */
	T persist(T entity);

	/**
	 * Persists the specified entity, with the possibility to flush the session
	 * to database.
	 * 
	 * @param entity
	 *            the entity to save
	 * @param flush
	 *            flush the session
	 * @return a reference to the saved entity
	 */
	T persist(T entity, boolean flush);

	/**
	 * Updates the specified entity.
	 * 
	 * @param object
	 *            the entity to update
	 * @return a reference to the updated entity
	 */
	T update(T object);

	/**
	 * Updates the specified entity.
	 * 
	 * @param object
	 *            the entity to update
	 * @param flush
	 *            flush the session
	 * @return a reference to the updated entity
	 */
	T update(T object, boolean flush);

	/**
	 * Deletes the specified entity by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	void delete(K id);

	/**
	 * Deletes the specified entity.
	 * 
	 * @param object
	 *            the entity
	 */
	void remove(T object);

	/**
	 * Finds persistent entities by Example provided Optionally, you can provide
	 * a list of names of all properties that should not be considered.
	 * 
	 * @param exampleEntityInstance
	 *            the example entity instance
	 * @param excludeProperties
	 *            a list of excluded properties
	 * @return The list of entities
	 */
	List<T> findByExample(T exampleEntityInstance, String... excludeProperties);

	/**
	 * Finds persistent entities in range by Example provided Optionally, you
	 * can provide a list of names of all properties that should not be
	 * considered.
	 * 
	 * @param exampleEntityInstance
	 *            the example entity instance
	 * @param first
	 *            first of range to retrieve
	 * @param max
	 *            max number of rows to retrieve
	 * @param excludeProperties
	 *            a list of excluded properties
	 * @return The list of entities
	 */
	List<T> findByExample(T exampleEntityInstance, int first, int max,
			String... excludeProperties);

	/**
	 * Returns the total records of a findByExample query. You need to pass the
	 * same example instance and exclude properties as you do in your
	 * findByExmaple method.
	 * 
	 * @param exampleEntityInstance
	 *            the example instance
	 * @param excludeProperties
	 *            the properties to exclude in the example
	 * @return the total records of a findByExample query
	 */
	long countByExample(T exampleEntityInstance, String... excludeProperties);

	/**
	 * Finds persistent entities by Example provided Optionally, you can provide
	 * a list of names of all properties that should not be considered.
	 * 
	 * @param exampleEntityInstance
	 *            the example entity instance
	 * @param sorting
	 *            List of Sort items, specifying the fields to sort and their
	 *            order.
	 * @param excludeProperties
	 *            a list of excluded properties
	 * @return The list of entities
	 */
	List<T> findByExample(T exampleEntityInstance, List<Sort> sorting,
			String... excludeProperties);

	/**
	 * Finds persistent entities in range by Example provided Optionally, you
	 * can provide a list of names of all properties that should not be
	 * considered.
	 * 
	 * @param exampleEntityInstance
	 *            the example entity instance
	 * @param first
	 *            first of range to retrieve
	 * @param max
	 *            max number of rows to retrieve
	 * @param sorts
	 *            list of sorts
	 * @param excludeProperties
	 *            a list of excluded properties
	 * @return The list of entities
	 */
	List<T> findByExample(T exampleEntityInstance, int first, int max,
			List<Sort> sorts, String... excludeProperties);

	List<T> findByProperty(String prop, Object val);
	
}