package com.sm.common.persistence.dao.impl;

import com.sm.common.*;
import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Limit;
import com.sm.common.criterion.Projection;
import com.sm.common.criterion.Restrictions;
import com.sm.common.persistence.dao.GenericDao;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.dao.SQLBuilder;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Created by Newbody on 12/2/2015.
 */
public class GenericDaoImpl<E extends Entity, PK extends Serializable> implements GenericDao<E, PK> {
    @Resource(name = "namedParameterJdbcTemplate")
    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected Class<E> entityClass;
    protected String entityName;
    protected String repositoryName;

    public GenericDaoImpl() {
        Class c = getClass();
        ParameterizedType ptype = null;
        do { // 遍历所有超类，直到找泛型定义
            try {
                ptype = (ParameterizedType) c.getGenericSuperclass();
            } catch (Exception e) {
            }
            c = c.getSuperclass();
        } while (ptype == null && c != null);
        if (ptype == null) {
            System.out.println("子类中没有定义泛型的具体类型");
        } else {
            this.entityClass = (Class<E>) ptype.getActualTypeArguments()[0];
        }
        this.repositoryName = PersistenceHelperFacade.toTableName(entityClass.getSimpleName());
        this.entityName = entityClass.getSimpleName();
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    /**
     * 持久化一个实体。
     *
     * @param entity 处于临时状态的实体。
     */
    @Override
    public Boolean create(E entity) {
        Map<String, Object> valueMap = PersistenceHelperFacade.toMap(entity);
        Creation creation = new Creation(entityClass);
        creation.setValueMap(valueMap);
        Integer jdbcResult = jdbcTemplate.update(creation.toNamedSqlString().toString(), valueMap);
        if (jdbcResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 持久化多个实体。
     *
     * @param entities 处于临时状态的实体的集合。
     */
    @Override
    public Boolean create(Collection<E> entities) {
        for (E e : entities) {
            create(e);
        }
        return true;
    }

    @Override
    public Boolean delete(PK id) {
        Removal removal = new Removal(entityClass);
        removal.add(Restrictions.eq("id", id));
        return delete(removal);
    }

    @Override
    public Boolean delete(String propertyName, Object value) {
        Removal removal = new Removal(entityClass);
        removal.add(Restrictions.eq(propertyName, value));
        return delete(removal);
    }

    @Override
    public Boolean delete(Criterion... criterions) {
        Removal removal = new Removal(entityClass);
        removal.add(criterions);
        return delete(removal);
    }

    private Boolean delete(Removal removal) {
        Integer jdbcResult = jdbcTemplate.update(removal.toNamedSqlString().toString(), removal.getNamedParameterValues());
        if (jdbcResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(E entity) {
        Modification modification = new Modification(entityClass);
        modification.setValueMap(PersistenceHelperFacade.toMap(entity));
        modification.add(Restrictions.eq("id", entity.getId()));
        return update(modification);
    }

    @Override
    public Boolean update(Collection<E> entities) {
        return null;
    }

    @Override
    public Boolean updateById(String id, String propertyName, Object propertyValue) {
        Modification modification = new Modification(entityClass);
        modification.setValue(propertyName, propertyValue);
        modification.add(Restrictions.eq("id", id));
        return update(modification);
    }

    @Override
    public Boolean updateById(String id, String[] propertyNames, Object[] propertyValues) {
        Modification modification = new Modification(entityClass);
        modification.setValue(propertyNames, propertyValues);
        modification.add(Restrictions.eq("id", id));
        return update(modification);
    }

    @Override
    public Boolean updateById(String id, Map<String, Object> valueMap) {
        Modification modification = new Modification(entityClass);
        modification.setValueMap(valueMap);
        modification.add(Restrictions.eq("id", id));
        return update(modification);
    }

    @Override
    public Boolean update(Map<String, Object> valueMap, Criterion... criterions) {
        Modification modification = new Modification(entityClass);
        modification.add(criterions);
        modification.setValueMap(valueMap);
        return update(modification);
    }

    private Boolean update(Modification modification) {
        Integer jdbcResult = jdbcTemplate.update(modification.toNamedSqlString().toString(), modification.getNamedParameterValues());
        if (jdbcResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E retrieveById(PK id) {
        return retrieve(Restrictions.eq("id", id));
    }

    @Override
    public E retrieve(String propertyName, Object value) {
        return retrieve(Restrictions.eq(propertyName, value));
    }

    @Override
    public E retrieve(Criterion... criterions) {
        return retrieve(new ArrayList<String>(), criterions);
    }

    @Override
    public E retrieve(Collection<String> propertyNames, Criterion... criterions) {
        Query query = new Query(this.entityClass);
        query.setPropertyNames(propertyNames);
        query.add(criterions);
        Map<String, Object> valueMap = jdbcTemplate.queryForMap(query.toNamedSqlString().toString(), query.getNamedParameterValues());
        return PersistenceHelperFacade.fromMap(entityClass, valueMap);
    }

    @Override
    public List<E> retrieveList() {
        Query query = new Query(this.entityClass);
        return retrieveList(query);
    }

    @Override
    public List<E> retrieveList(Criterion criterion) {
        Query query = new Query(this.entityClass);
        query.add(criterion);
        return retrieveList(query);
    }

    @Override
    public List<E> retrieveList(Order order, Limit limit) {
        Query query = new Query(this.entityClass);
        query.addOrder(order);
        query.setLimit(limit);
        return retrieveList(query);
    }

    @Override
    public List<E> retrieveList(Criterion criterion, Order order, Limit limit) {
        Query query = new Query(this.entityClass);
        query.add(criterion);
        query.addOrder(order);
        query.setLimit(limit);
        return retrieveList(query);
    }

    @Override
    public List<E> retrieveList(Collection<String> propertyNames, Criterion criterion, Order order, Limit limit) {
        Query query = new Query(this.entityClass);
        query.setPropertyNames(propertyNames);
        query.add(criterion);
        query.addOrder(order);
        query.setLimit(limit);
        return retrieveList(query);
    }

    @Override
    public List<E> retrieveList(Collection<String> propertyNames, Collection<Criterion> criterions, Collection<Order> orders, Limit limit) {
        Query query = new Query(this.entityClass);
        query.setPropertyNames(propertyNames);
        query.add(criterions);
        query.addOrder(orders);
        query.setLimit(limit);
        return retrieveList(query);
    }

    private List<E> retrieveList(Query query) {
        List<Map<String, Object>> valueMapList = jdbcTemplate.queryForList(query.toNamedSqlString(), query.getNamedParameterValues());
        return mapListToEntity(valueMapList);
    }

    private List<E> mapListToEntity(List<Map<String, Object>> valueMapList) {
        List<E> entityList = new ArrayList<>();
        for (Map<String, Object> map : valueMapList) {
            entityList.add(PersistenceHelperFacade.fromMap(entityClass, map));
        }
        return entityList;
    }

    @Override
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz) {
        ProjectionQuery query = new ProjectionQuery(entityClass);
        return retrieveProjection(query, clazz);
    }

    @Override
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz, Criterion... criterions) {
        ProjectionQuery query = new ProjectionQuery(entityClass);
        query.add(criterions);
        return retrieveProjection(query, clazz);
    }

    @Override
    public <T> T retrieveByProjection(Projection projection, Class<T> clazz, Collection<Criterion> criterions) {
        ProjectionQuery query = new ProjectionQuery(entityClass);
        query.add(criterions);
        return retrieveProjection(query, clazz);
    }

    private <T> T retrieveProjection(ProjectionQuery query, Class<T> clazz){
        T result = jdbcTemplate.queryForObject(query.toNamedSqlString().toString(), query.getNamedParameterValues(), clazz);
        return result;
    }

    @Override
    public Boolean exists(Criterion... criterions) {
        StringBuilder sql = new StringBuilder("SELECT EXISTS (SELECT * FROM ").append(repositoryName);
        sql.append(" WHERE ");
        Map<String, Object> params = new HashMap<>();
        Iterator<Criterion> iterator = Arrays.asList(criterions).iterator();
        while (iterator.hasNext()){
            Criterion criterion = iterator.next();
            sql.append(criterion.toNamedSqlString());
            if(iterator.hasNext()){
                sql.append(" AND ");
            }
            params.putAll(criterion.getNamedParameterValues());
        }
        sql.append(")");
        Long result = jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}
