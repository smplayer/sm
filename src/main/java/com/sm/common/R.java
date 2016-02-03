package com.sm.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 10/12/2015.
 */
public class R<T> {
    private T result;
    private Map<String, Object> dataMap = new HashMap<>();

    public R(){

    }

    public R(T result) {
        this.result = result;
    }

    public T getResult(){
        return this.result;

    }

    public R<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public Object getData(String dataName){
        return this.dataMap.get(dataName);
    }

    public R<T> addData(String dataName, Object data){
        this.dataMap.put(dataName, data);
        return this;
    }

}
