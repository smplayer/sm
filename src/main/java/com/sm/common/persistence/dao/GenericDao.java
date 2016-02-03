package com.sm.common.persistence.dao;

import com.sm.common.Order;
import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Limit;
import com.sm.common.criterion.Projection;
import com.sm.common.entity.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Newbody on 12/2/2015.
 */
public interface GenericDao<E extends Entity, PK extends Serializable> {
    Class<E> getEntityClass();

    String getEntityName();
//    public E findAndLock(PK id, LockMode lockMode);
    public Boolean create(E entity);
    public Boolean create(Collection<E> entities);

    public Boolean delete(PK id);
    public Boolean delete(String propertyName, Object value);
//    public Boolean delete(String[] propertyNames, Object[] values);
//    public Boolean delete(Map<String, Object> propertyConstraint);
    public Boolean delete(Criterion... criterion);

    public Boolean update(E entity);
    public Boolean update(Collection<E> entities);
    public Boolean updateById(String id, String propertyName, Object propertyValue);
    public Boolean updateById(String id, String propertyNames[], Object propertyValues[]);
    public Boolean updateById(String id, Map<String, Object> valueMap);
//    public Boolean update(String constrainedPropertyName, Object constrainedPropertyValue, String targetPropertyName, Object targetPropertyValue);
//    public Boolean update(String constrainedPropertyName, Object constrainedPropertyValue, String targetPropertyNames[], Object targetPropertyValues[]);
//    public Boolean update(String constrainedPropertyName, Object constrainedPropertyValue, Map<String, Object> valueMap);
//    public Boolean update(Map<String, Object> constraint, Map<String, Object> valueMap);
    public Boolean update(Map<String, Object> valueMap, Criterion... criterion);

//    public Boolean createOrUpdate(E entity);
//    public Boolean createOrUpdate(Collection<E> entities);
//    public Boolean merge(E entity);
//    public Boolean merge(Collection<E> entities);
//    public Boolean delete(E entity);
//    public Boolean delete(Collection<E> entities);
//    public Boolean bulkDelete(Class<E> clazz, String[] propertyNames, Object[] values);
//    public Boolean refresh(Object entity);
//    public List<E> findAll();
//    public List<E> findByOrder(String orderPropertyName, SQLOrderMode orderMode);

    public E retrieveById(PK id);
    public E retrieve(String propertyName, Object value);
    public E retrieve(Criterion... criterions);
    public E retrieve(Collection<String> propertyNames, Criterion... criterions);
    public List<E> retrieveList();
    public List<E> retrieveList(Criterion criterion);
    public List<E> retrieveList(Order order, Limit limit);
    public List<E> retrieveList(Criterion criterion, Order order, Limit limit);
    public List<E> retrieveList(Collection<String> propertyNames, Criterion criterion, Order order, Limit limit);
//    public List<E> retrieveList(Collection<Criterion> criterions, Collection<Order> orders, Limit limit);
    public List<E> retrieveList(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Limit limit);

    /*MAX, MIN, COUNT, SUM, EXISTS*/
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz);
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz, Criterion... criterions);
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz, Collection<Criterion> criterions);
    public Boolean exists(Criterion... criterions);

}
