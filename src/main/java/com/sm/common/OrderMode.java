package com.sm.common;

/**
 * Created by Newbody on 12/2/2015.
 */
public enum OrderMode {
    ASC("ASC"), DESC("DESC");

    private final String value;

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    OrderMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
