package com.sm.common.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Entity {
    protected String id;
    protected Long createdTime;
    protected Long lastUpdatedTime;
    protected String status;
//    protected Boolean persistent;

    public Entity(){
//        setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
    public Entity(Map<String, Object> map){
        this.id = (String) map.get("id");
        this.createdTime = (Long) map.get("createdTime");
        this.lastUpdatedTime = (Long) map.get("lastUpdatedTime");
        this.status = (String) map.get("status");
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("createdTime", this.createdTime);
        map.put("lastUpdatedTime", this.lastUpdatedTime);
        map.put("status", this.status);
        return map;
    }
}
