package com.sm.common.criterion;

import java.util.*;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Junction implements Criterion {
    private final Nature nature;
    private final List<Criterion> conditions = new ArrayList<Criterion>();

    protected Junction(Nature nature) {
        this.nature = nature;
    }

    protected Junction(Nature nature, Criterion... criterion) {
        this(nature);
        Collections.addAll(conditions, criterion);
    }


    public Junction add(Criterion criterion) {
        conditions.add(criterion);
        return this;
    }

    public Nature getNature() {
        return nature;
    }

    public Iterable<Criterion> conditions() {
        return conditions;
    }

    @Override
    public String toNamedSqlString() {
        if (conditions.size() == 0) {
            return "1=1";
        }

        final StringBuilder buffer = new StringBuilder().append('(');
        final Iterator itr = conditions.iterator();
        while (itr.hasNext()) {
            buffer.append(((Criterion) itr.next()).toNamedSqlString());
            if (itr.hasNext()) {
                buffer.append(' ')
                        .append(nature.getOperator())
                        .append(' ');
            }
        }

        return buffer.append(')').toString();
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        final Map<String, Object> values = new HashMap<>();
        for (Criterion condition : conditions) {
            final Map<String, Object> subValues = condition.getNamedParameterValues();
            values.putAll(subValues);
        }
        return values;
    }

    public static enum Nature {
        /**
         * An AND
         */
        AND,
        /**
         * An OR
         */
        OR;

        /**
         * The corresponding SQL operator
         *
         * @return SQL operator
         */
        public String getOperator() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
