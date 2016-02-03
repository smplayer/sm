package com.sm.common.service;

import com.sm.common.Order;
import com.sm.common.R;
import com.sm.common.Slice;
import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Limit;
import com.sm.common.criterion.Projection;
import com.sm.common.entity.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Newbody on 12/2/2015.
 */
public interface GenericService<E extends Entity, PK extends Serializable> {
    R<Boolean> create(E entity);
    R<Boolean> create(Collection<E> entities);

    R<Boolean> remove(PK id);
    R<Boolean> remove(String propertyName, Object value);
    R<Boolean> remove(Collection<PK> ids);
    R<Boolean> reomve(Criterion... criterions);

    R<Boolean> modify(E entity);
    R<Boolean> modify(Collection<E> entities);

    E getById(PK id);
    E get(String propertyName, Object value);
    E get(Criterion... criterions);

    List<E> getListByIds(Collection<PK> ids);
    List<E> getList();
    List<E> getList(Order order, Limit limit);
    List<E> getList(Criterion criterion, Order order, Limit limit);
    List<E> getList(Collection<String> propertyNames, Criterion criterion, Order order, Limit limit);
    List<E> getList(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Limit limit);
    void getBySlice(Slice<E> slice);
    void getBySlice(Collection<String> propertyNames, Criterion criterion, Order order, Slice<E> slice);
    void getBySlice(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Slice<E> slice);

    public<T> T getByProjection(Projection projection, Class<T> clazz);
    public<T> T getByProjection(Projection projection, Class<T> clazz, Criterion... criterions);
    public<T> T getByProjection(Projection projection, Class<T> clazz, Collection<Criterion> criterions);

    /*slice*/
//    void slice(Slice<E> slicer);
//    void slice(Slice<E> slicer, Map<String, Object> propertyConstraint);
//    void slice(Slice<E> slicer, Collection<Order> orderConstraints);
//    void slice(Slice<E> slicer, Map<String, Object> propertyConstraint, Collection<Order> orderConstraints);

    /*max*/
//    Integer maxAsInteger(String targetPropertyName);
//    Integer maxAsInteger(String targetPropertyName, String constrainedPropertyName, String constrainedPropertyValue);
//    Integer maxAsInteger(String targetPropertyName, Map<String, Object> constraint);
//    Long maxAsLong(String targetPropertyName);
//    Long maxAsLong(String targetPropertyName, String constrainedPropertyName, String constrainedPropertyValue);
//    Long maxAsLong(String targetPropertyName, Map<String, Object> constraint);
//    Double maxAsDouble(String targetPropertyName);
//    Double maxAsDouble(String targetPropertyName, String constrainedPropertyName, String constrainedPropertyValue);
//    Double maxAsDouble(String targetPropertyName, Map<String, Object> constraint);
}
