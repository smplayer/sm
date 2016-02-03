package com.sm.common.service.impl;

import com.sm.common.Order;
import com.sm.common.R;
import com.sm.common.Slice;
import com.sm.common.criterion.*;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.dao.GenericDao;
import com.sm.common.service.GenericService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Newbody on 12/2/2015.
 */
public abstract class GenericServiceImpl<E extends Entity, PK extends Serializable> implements GenericService<E, PK> {
    /**
     * 目前主要用途是使用发布事件applicationContext.publishEvent
     */
    @Autowired
    private ApplicationContext applicationContext;

    protected abstract GenericDao<E, PK> getDao();

    @Override
    public R<Boolean> create(E entity) {
        initEntity(entity);
        Boolean pr = getDao().create(entity);
        R<Boolean> r = new R<>(pr);
        r.addData("createdEntity", entity);
        return r;
    }

    @Override
    public R<Boolean> create(Collection<E> entities) {
        for (E e : entities) {
            create(e);
        }
        R<Boolean> r = new R<>(true);
        return r;
    }

    @Override
    public R<Boolean> remove(PK id) {
        getDao().delete(id);
        R<Boolean> r = new R<>(true);
        return r;
    }

    @Override
    public R<Boolean> remove(Collection<PK> ids) {
        for (PK id : ids) {
            remove(id);
        }
        R<Boolean> r = new R<>(true);
        return r;
    }

    @Override
    public R<Boolean> remove(String propertyName, Object value) {
        Boolean pr = getDao().delete(propertyName, value);
        return new R<>(pr);
    }

    @Override
    public R<Boolean> reomve(Criterion... criterions) {
        Boolean pr = getDao().delete(criterions);
        return new R<>(pr);
    }

    @Override
    public R<Boolean> modify(E entity) {
        refreshUpdatedTime(entity);
        Boolean pr = getDao().update(entity);
        R<Boolean> r = new R<>(pr);
        return r;
    }

    @Override
    public R<Boolean> modify(Collection<E> entities) {
        for (E e : entities) {
            modify(e);
        }
        R<Boolean> r = new R<>(true);
        return r;
    }

    @Override
    public E getById(PK id) {
        return getDao().retrieveById(id);
    }

    @Override
    public E get(String propertyName, Object value) {
        return getDao().retrieve(propertyName, value);
    }

    @Override
    public E get(Criterion... criterions) {
        return getDao().retrieve(criterions);
    }

    @Override
    public List<E> getListByIds(Collection<PK> ids) {
        return getDao().retrieveList(Restrictions.in("id", ids));
    }

    @Override
    public List<E> getList() {
        return getDao().retrieveList();
    }

    @Override
    public List<E> getList(Order order, Limit limit) {
        return getDao().retrieveList(order, limit);
    }

    @Override
    public List<E> getList(Criterion criterion, Order order, Limit limit) {
        return getDao().retrieveList(criterion, order, limit);
    }

    @Override
    public List<E> getList(Collection<String> propertyNames, Criterion criterion, Order order, Limit limit) {
        return getDao().retrieveList(propertyNames, criterion, order, limit);
    }

    @Override
    public List<E> getList(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Limit limit) {
        return getDao().retrieveList(propertyNames, criterions, orders, limit);
    }

    @Override
    public void getBySlice(Slice<E> slice) {
        getBySlice(new ArrayList<String>(), new ArrayList<Criterion>(), new ArrayList<Order>(), slice);
    }

    @Override
    public void getBySlice(Collection<String> propertyNames, Criterion criterion, Order order, Slice<E> slice) {
        getBySlice(propertyNames, Arrays.asList(criterion), Arrays.asList(order), slice);
    }

    @Override
    public void getBySlice(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Slice<E> slice) {
        List<E> dataList = getList(propertyNames, criterions, orders, new Limit(slice.getDataIndex(), slice.getSliceSize()));
        slice.setDataList(dataList);
        Long quantity = getByProjection(Projections.rowCount(), Long.class, criterions);
        slice.setDataQuantity(quantity);
    }

    @Override
    public <T> T getByProjection(Projection projection, Class<T> clazz) {
        return getDao().retrieveByProjection(projection, clazz);
    }

    @Override
    public <T> T getByProjection(Projection projection, Class<T> clazz, Criterion... criterions) {
        return getDao().retrieveByProjection(projection, clazz, criterions);
    }

    @Override
    public <T> T getByProjection(Projection projection, Class<T> clazz, Collection<Criterion> criterions) {
        return getDao().retrieveByProjection(projection, clazz, criterions);
    }

    public void initEntity(E entity) {
        if (StringUtils.isBlank(entity.getId()))
            entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        entity.setCreatedTime(new Date().getTime());
        entity.setLastUpdatedTime(new Date().getTime());
    }

    public void initEntity(Collection<E> entities) {
        for (E e : entities) {
            initEntity(e);
        }
    }

    public void refreshUpdatedTime(Entity entity) {
        entity.setLastUpdatedTime(new Date().getTime());
    }

    public void refreshUpdatedTime(Collection<E> entities) {
        for (E e : entities) {
            refreshUpdatedTime(e);
        }
    }
}
