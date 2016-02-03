package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Conjunction extends Junction {
    public Conjunction() {
        super(Nature.AND);
    }

    protected Conjunction(Criterion... criterion) {
        super(Nature.AND, criterion);
    }
}
