package com.sm.common;

import com.sm.common.criterion.Criterion;
import com.sm.common.criterion.Restrictions;
import com.sm.common.entity.Entity;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import com.sm.user.entity.User;

import java.util.*;

/**
 * Created by Newbody on 2016/1/8.
 */
public class Modification {
    private Class<? extends Entity> entityClass;
    private Map<String, Object> valueMap;
    private List<Criterion> criterions = new ArrayList<>();

    public Modification(Class<? extends Entity> entityClass){
        this.entityClass = entityClass;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public Modification setValue(String propertyName, Object value){
        if(valueMap == null){
            valueMap = new HashMap<>();
        }
        valueMap.put(propertyName, value);
        return this;
    }

    public Modification setValue(String[] propertyNames, Object[] values){
        if(valueMap == null){
            valueMap = new HashMap<>();
        }
        for(Integer i = 0; i < propertyNames.length; i++){
            valueMap.put(propertyNames[i], values[i]);
        }
        return this;
    }

    public Modification add(Criterion criterion) {
        this.criterions.add(criterion);
        return this;
    }

    public Modification add(Criterion... criterions) {
        this.criterions.addAll(Arrays.asList(criterions));
        return this;
    }

    public Modification add(Collection<Criterion> criterions) {
        this.criterions.addAll(criterions);
        return this;
    }

    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder("UPDATE ").append(PersistenceHelperFacade.toTableName(entityClass.getSimpleName()));
        builder.append(" SET ");
        Iterator<String> propertyNameIterator = valueMap.keySet().iterator();
        while (propertyNameIterator.hasNext()) {
            String name = propertyNameIterator.next();
            builder.append(PersistenceHelperFacade.toColumnName(name))
                    .append("=:new_")
                    .append(name);
            if (propertyNameIterator.hasNext()) {
                builder.append(",");
            }
        }
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
        for(Map.Entry<String, Object> e : valueMap.entrySet()){
            values.put("new_" + e.getKey(), e.getValue());
        }
        for(Criterion criterion : criterions){
            values.putAll(criterion.getNamedParameterValues());
        }
        return values;
    }

    public static void main(String[] args) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("username", "newbody");
        vm.put("status", "ok");
        Modification modification = new Modification(User.class);
        modification.setValueMap(vm);
        modification.add(Restrictions.between("status", 1, 9));
        System.out.println(modification.toNamedSqlString());
    }
}
