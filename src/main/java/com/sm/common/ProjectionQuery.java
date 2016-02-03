package com.sm.common;

import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Projection;
import com.sm.common.criterion.Projections;
import com.sm.common.criterion.Restrictions;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import com.sm.user.entity.User;

import java.util.*;

/**
 * Created by Newbody on 2016/1/9.
 */
public class ProjectionQuery {
    private Class<? extends Entity> entityClass;
    private Projection projection;
    private List<Criterion> criterions = new ArrayList<>();
    public ProjectionQuery(Class<? extends Entity> entityClass){
        this.entityClass = entityClass;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public ProjectionQuery add(Criterion criterion) {
        this.criterions.add(criterion);
        return this;
    }

    public ProjectionQuery add(Criterion... criterions) {
        this.criterions.addAll(Arrays.asList(criterions));
        return this;
    }

    public ProjectionQuery add(Collection<Criterion> criterions) {
        this.criterions.addAll(criterions);
        return this;
    }

    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder("SELECT ")
                .append(projection.toSqlString())
                .append(" FROM ")
                .append(PersistenceHelperFacade.toTableName(entityClass.getSimpleName()))
                .append(" WHERE ");
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
        ProjectionQuery query = new ProjectionQuery(User.class);
        query.setProjection(Projections.max("username"));
        query.add(Restrictions.eq("status", "ok"));
        System.out.println(query.toNamedSqlString());
    }
}
