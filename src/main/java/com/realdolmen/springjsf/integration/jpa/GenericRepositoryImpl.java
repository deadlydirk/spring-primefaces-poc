package com.realdolmen.springjsf.integration.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;

import com.realdolmen.springjsf.domain.AbstractEntity;
import com.realdolmen.springjsf.integration.GenericRepository;
import com.realdolmen.springjsf.integration.Sort;
import com.realdolmen.springjsf.integration.Sort.SortOrder;

/**
 * JPA based implementation of GenericRepository.
 * 
 * @param <T>
 *            The JPA entity for which this repository is working.
 * @param <K>
 *            The identifier type (Primary Key) of the entity.
 */
public abstract class GenericRepositoryImpl<T extends AbstractEntity<K, ?>, K>
		implements GenericRepository<T, K> {

    /**
     * The JPA entity manager.
     */
	@PersistenceContext
	private EntityManager entityManager;
	
	 /**
     * The entity type.
     */
    private final Class<T> entityType;


    /**
     * Constructs a GenericRepositoryImpl object.
     */
    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        // this.entityType = (Class<T>) ((ParameterizedType)
        // getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        //
        // find below a more complete method.

        Object genericSuperclass = this.getClass().getGenericSuperclass();
        Type typeArgument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];

        // Verify if the resulting typeArgument is an instance of
        // ParameterizedType.
        // When this is the case the generic superclass uses generics. To avoid
        // an UnsatisfiedDependencyException due to a ClassCastException we have
        // to take the raw type. This raw type can be casted to a Class<T>.
        if (typeArgument instanceof ParameterizedType) {
            typeArgument = ((ParameterizedType) typeArgument).getRawType();
        }
        this.entityType = (Class<T>) typeArgument;
    }

    /**
     * Extra constructor which allows an implementor of this class to set the
     * entity type at construction time when the above method is not sufficient
     * for the task.
     * 
     * @param entityType
     *            the entityType to set for this repository.
     */
    protected GenericRepositoryImpl(final Class<T> entityType) {
        this.entityType = entityType;
    }

    // public GenericRepositoryImpl() {
    // }

    /**
     * Sets the entityManager.
     * 
     * @param entityManager
     *            the entityManager.
     */
    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findById(final K id) {
        Validate.notNull(id, "The id cannot be null");
        return entityManager.find(entityType, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public T findByIdEager(final K id, final String... properties) {
        return (T) findByIdEagerInternal(entityType, id, properties);
    }

    /**
     * Loads a persistent {@link Object} from the database, from a specific ID.
     * The given properties are eagerly fetch together with the persistent {@link Object}, making them available for further use.
     * 
     * Only properties of many-to-one or many-to-many relations can be given.
     * 
     * @param clazz
     *            the {@link Class}
     * @param id
     *            the unique identifier of the {@link Object}
     * @param properties
     *            the properties to eagerly fetch.
     * @return the {@link Object}, throws {@link org.hibernate.HibernateException} if there is more then
     *         one matching result
     */
    private Object findByIdEagerInternal(final Class<?> clazz, final K id, final String... properties) {
        HibernateEntityManager hem = (HibernateEntityManager) getEntityManager();
        Session session = hem.getSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.idEq(id));

        for (String property : properties) {
            criteria.createCriteria(property, CriteriaSpecification.LEFT_JOIN);
        }

        return criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T persist(final T entity) {
        Validate.notNull(entity, "The entity cannot be null");
        entityManager.persist(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T persist(final T entity, final boolean flush) {
        persist(entity);
        if (flush) {
            entityManager.flush();
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(final T object) {
        Validate.notNull(object, "The object cannot be null");
        return entityManager.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(final T object, final boolean flush) {
        T updated = update(object);
        if (flush) {
            entityManager.flush();
        }
        return updated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("Select t from " + entityType.getSimpleName() + " t ORDER BY t.id").getResultList(); // NOPMD
        // by
        // duboiss
        // on
        // 18/03/09
        // 12:12
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(final int first, final int max) {
        return entityManager.createQuery("Select t from " + entityType.getSimpleName() + " t ORDER BY t.id").setFirstResult(first)
                .setMaxResults(max).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAll() {
        return (Long) entityManager.createQuery("Select count(t) from " + entityType.getSimpleName() + " t").getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(final List<Sort> sorting) {
        final StringBuilder query = new StringBuilder("Select t from ");
        query.append(entityType.getSimpleName()).append(" t");
        appendOrderBy(query, sorting);

        return entityManager.createQuery(query.toString()).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(final List<Sort> sorting, final int first, final int max) {
        final StringBuilder query = new StringBuilder("Select t from ");
        query.append(entityType.getSimpleName()).append(" t");
        appendOrderBy(query, sorting);
        return entityManager.createQuery(query.toString()).setFirstResult(first).setMaxResults(max).getResultList();
    }

    /**
     * Helper method to add an order clause to a JPA query.
     * 
     * @param query
     *            the query
     * @param sorting
     *            the sorting list
     */
    private void appendOrderBy(final StringBuilder query, final List<Sort> sorting) {
        if (sorting != null && !sorting.isEmpty()) {
            query.append(" ORDER BY ");
            for (Sort sort : sorting) {
                Validate.notEmpty(sort.getFieldName(), "The fieldName cannot be null");
                query.append(sort.getFieldName());
                query.append(" ").append(sort.getSortOrder() == SortOrder.ASCENDING ? "ASC" : "DESC");
                query.append(", ");
            }
            // Remove last ", "
            query.delete(query.length() - 2, query.length());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final K id) {
        Validate.notNull(id, "The id cannot be null");
        entityManager.remove(this.findById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final T object) {
        Validate.notNull(object, "The entity cannot be null");
        entityManager.remove(object);
    }

    /**
     * Find by property method.
     * 
     * @param prop
     *            the property to check.
     * @param val
     *            the value property prop should have to be included in the
     *            result
     * @return all entities where property prop has value val
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(final String prop, final Object val) {
        final Query query = entityManager.createQuery("select x from " + getEntityType().getSimpleName() + " x where x." + prop
                + " = ?1");
        query.setParameter(1, val);
        return query.getResultList();
    }

    /**
     * Implementation specific method to be used in subclasses, using
     * hibernate's Criteria API. The list of criterions will be added in the
     * same order to the {@link Criteria} object, the list of orders will be
     * added in the same order to the {@link Criteria} object. Both list may be
     * null.
     * 
     * @param criterions
     *            list of criterions
     * @param ordening
     *            list of orders
     * @return a list of entities, having the necessary criteria, in the given
     *         order.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(final List<Criterion> criterions, final List<Order> ordening) {
        final HibernateEntityManager hem = (HibernateEntityManager) entityManager;

        final Session session = hem.getSession();
        final Criteria criteria = session.createCriteria(entityType);

        if (criterions != null) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }

        if (ordening != null) {
            for (Order order : ordening) {
                criteria.addOrder(order);
            }
        }

        return criteria.list();
    }

    /**
     * Implementation specific method to be used in subclasses, using
     * hibernate's Criteria API. The list of criterions will be added in the
     * same order to the {@link Criteria} object. The list may be null.
     * 
     * @param criterions
     *            list of criterions
     * @return a list of entities, having the necessary criteria.
     */
    protected List<T> findByCriteria(final List<Criterion> criterions) {
        return findByCriteria(criterions, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findByExample(final T exampleEntityInstance, final String... excludeProperties) {
        return findByExample(exampleEntityInstance, -1, -1, null, excludeProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countByExample(final T exampleEntityInstance, final String... excludeProperties) {
        final Example example = Example.create(exampleEntityInstance).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE);
        for (String exclude : excludeProperties) {
            example.excludeProperty(exclude);
        }
        HibernateEntityManager hem = (HibernateEntityManager) entityManager;
        Session session = hem.getSession();
        Criteria criteria = session.createCriteria(entityType);
        criteria.add(example);
        criteria.setProjection(Projections.rowCount());

        return (Integer) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findByExample(final T exampleEntityInstance, final List<Sort> sorting, final String... excludeProperties) {
        return findByExample(exampleEntityInstance, -1, -1, sorting, excludeProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findByExample(final T exampleEntityInstance, final int first, final int max, final String... excludeProperties) {
        return findByExample(exampleEntityInstance, first, max, null, excludeProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByExample(final T exampleEntityInstance, final int first, final int max, final List<Sort> sorting,
            final String... excludeProperties) {
        final Example example = Example.create(exampleEntityInstance).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE);
        for (String exclude : excludeProperties) {
            example.excludeProperty(exclude);
        }
        HibernateEntityManager hem = (HibernateEntityManager) entityManager;
        Session session = hem.getSession();
        Criteria criteria = session.createCriteria(entityType);
        criteria.add(example);
        if (first != -1) {
            criteria.setFirstResult(first);
            criteria.setMaxResults(max);
        }
        if (sorting != null) {
            String fieldName;
            for (Sort sort : sorting) {
                fieldName = sort.getFieldName();
                Validate.notEmpty(fieldName, "Field name should not be empty.");
                criteria.addOrder((sort.getSortOrder() == SortOrder.ASCENDING ? Order.asc(fieldName) : Order.desc(fieldName)));
            }
        }
        return criteria.list();
    }

    /**
     * Gets the entity type.
     * 
     * @return the entity type
     */
    public Class<T> getEntityType() {
        return entityType;
    }

    /**
     * Returns the entityManager.
     * 
     * @return the entityManager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
