package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Disjunction extends Junction {
    protected Disjunction() {
        super(Nature.OR);
    }

    protected Disjunction(Criterion[] conditions) {
        super(Nature.OR, conditions);
    }
}
