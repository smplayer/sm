package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/7.
 */
public class Limit {
    private Long start = 0L;
    private Integer size = 1000;

    public Limit(){}
    public Limit(Integer size){
        this.size = size;
    }
    public Limit(Long start, Integer size){
        this.start = start;
        this.size = size;
    }

    public Limit start(Long start) {
        this.start = start;
        return this;
    }

    public Limit size(Integer size) {
        this.size = size;
        return this;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String toSqlString() {
        return "LIMIT " + start + "," + size;
    }
}
