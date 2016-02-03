package com.sm.common;

import com.sm.common.criterion.Criterion;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import com.sm.user.entity.User;

import java.util.*;

/**
 * Created by Newbody on 2016/1/8.
 */
public class Removal {
    private Class<? extends Entity> entityClass;
    private List<Criterion> criterions = new ArrayList<>();

    public Removal(Class<? extends Entity> entityClass){
        this.entityClass = entityClass;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public Removal add(Criterion criterion) {
        this.criterions.add(criterion);
        return this;
    }

    public Removal add(Criterion... criterions) {
        this.criterions.addAll(Arrays.asList(criterions));
        return this;
    }

    public Removal add(Collection<Criterion> criterions) {
        this.criterions.addAll(criterions);
        return this;
    }

    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder("DELETE FROM ").append(PersistenceHelperFacade.toTableName(entityClass.getSimpleName()));
        builder.append(" WHERE ");
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
        return builder.toString();
    }

    public Map<String, Object> getNamedParameterValues(){
        Map<String, Object> values = new HashMap<>();
        for(Criterion criterion : criterions){
            values.putAll(criterion.getNamedParameterValues());
        }
        return values;
    }

    public static void main(String[] args) {
        Removal removal = new Removal(User.class);
        System.out.println(removal.toNamedSqlString());
    }

}
