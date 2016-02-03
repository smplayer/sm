package com.sm.common.criterion;

import java.util.*;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Restrictions {
    public static Criterion eq(String propertyName, Object value) {
        return value != null
                ? new SimpleExpression(propertyName, value, "=")
                : isNull(propertyName);
    }

//    public static Criterion eqOrIsNull(String propertyName, Object value) {
//        return value == null
//                ? isNull(propertyName)
//                : eq(propertyName, value);
//    }

    public static Criterion ne(String propertyName, Object value) {
        return value != null
                ? new SimpleExpression(propertyName, value, "<>")
                : isNotNull(propertyName);
    }

    public static Criterion neOrIsNotNull(String propertyName, Object value) {
        return value == null
                ? isNotNull(propertyName)
                : ne(propertyName, value);
    }

    public static Criterion like(String propertyName, Object value) {
        // todo : update this to use LikeExpression
        return new SimpleExpression(propertyName, value, " like ");
    }

    public static Criterion gt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">");
    }

    public static Criterion lt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<");
    }

    public static Criterion le(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<=");
    }

    public static Criterion ge(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">=");
    }

    public static Criterion between(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi);
    }

    public static Criterion in(String propertyName, Object[] values) {
        return new InExpression(propertyName, Arrays.asList(values));
    }

    public static Criterion in(String propertyName, Collection values) {
        return new InExpression(propertyName, new ArrayList<>(values));
    }

//    public static Criterion in(String propertyName, List<Object> values) {
//        return new InExpression(propertyName, values);
//    }

    public static Criterion isNull(String propertyName) {
        return new NullExpression(propertyName);
    }

    public static Criterion isNotNull(String propertyName) {
        return new NotNullExpression(propertyName);
    }

    public static Criterion eqProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, "=");
    }

    public static Criterion neProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, "<>");
    }

    public static Criterion ltProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, "<");
    }

    public static Criterion leProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, "<=");
    }

    public static Criterion gtProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, ">");
    }

    public static Criterion geProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, ">=");
    }

    public static Conjunction and(Criterion... predicates) {
        return conjunction(predicates);
    }

    public static Conjunction conjunction() {
        return new Conjunction();
    }

    public static Conjunction conjunction(Criterion... conditions) {
        return new Conjunction(conditions);
    }

    public static Disjunction or(Criterion... predicates) {
        return disjunction(predicates);
    }

    public static Disjunction disjunction() {
        return new Disjunction();
    }

    public static Disjunction disjunction(Criterion... conditions) {
        return new Disjunction(conditions);
    }

    public static Criterion allEq(Map<String, ?> propertyNameValues) {
        final Conjunction conj = conjunction();

        for (Map.Entry<String, ?> entry : propertyNameValues.entrySet()) {
            conj.add(eq(entry.getKey(), entry.getValue()));
        }
        return conj;
    }
}
