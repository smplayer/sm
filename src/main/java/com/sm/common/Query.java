package com.sm.common;

import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Limit;
import com.sm.common.criterion.Restrictions;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import com.sm.user.entity.User;

import java.util.*;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Query {
    private Class<? extends Entity> entityClass;
    private Collection<String> propertyNames;
    private List<Criterion> criterions = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private Limit limit;

    public Query(Class<? extends Entity> entityClass){
        this.entityClass = entityClass;
    }

    public Collection<String> getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(Collection<String> propertyNames) {
        this.propertyNames = propertyNames;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Query add(Criterion criterion) {
        this.criterions.add(criterion);
        return this;
    }

    public Query add(Criterion... criterions) {
        this.criterions.addAll(Arrays.asList(criterions));
        return this;
    }

    public Query add(Collection<Criterion> criterions) {
        this.criterions.addAll(criterions);
        return this;
    }

    public Query addOrder(Order... orders) {
        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    public Query addOrder(Collection<Order> orders) {
        this.orders.addAll(orders);
        return this;
    }

    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder("SELECT ");
        if(propertyNames == null || propertyNames.isEmpty()){
            propertyNames = PersistenceHelperFacade.getPersistablePropertyNames(entityClass);
        }
        Iterator<String> propertyNameIterator = propertyNames.iterator();
        while (propertyNameIterator.hasNext()){
            String name = propertyNameIterator.next();
            builder.append(PersistenceHelperFacade.toColumnName(name))
                    .append(" as ")
                    .append(name);
            if(propertyNameIterator.hasNext()){
                builder.append(",");
            }
        }
        builder.append(" FROM ").append(PersistenceHelperFacade.toTableName(entityClass.getSimpleName())).append(" WHERE ");
        Iterator<Criterion> criterionIterator = criterions.iterator();
        if(!criterionIterator.hasNext()){
            builder.append("1=1");
        }
        while (criterionIterator.hasNext()) {
            builder.append(criterionIterator.next().toNamedSqlString());
            if (criterionIterator.hasNext()) {
                builder.append(" AND ");
            }
        }
        Iterator<Order> orderIterator = orders.iterator();
        if (orderIterator.hasNext()) {
            builder.append(" ORDER BY ");
        }
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            builder.append(PersistenceHelperFacade.toColumnName(order.getPropertyName()))
                    .append(" ")
                    .append(order.getOrderMode().getValue());
            if (orderIterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(" LIMIT :limit_start,:limit_size");
        return builder.toString();
    }

    public Map<String, Object> getNamedParameterValues(){
        Map<String, Object> values = new HashMap<>();
        for(Criterion criterion : criterions){
            values.putAll(criterion.getNamedParameterValues());
        }
        values.put(":limit_start", limit.getStart());
        values.put(":limit_size", limit.getSize());
        return values;
    }

    public static void main(String[] args) {
        System.out.println(
                new Query(User.class)
                        .add(Restrictions.eq("id", "abc"), Restrictions.ne("status", "ok"))
                        .addOrder(Order.asc("id"), Order.desc("status"))
                        .toNamedSqlString());
    }

}
