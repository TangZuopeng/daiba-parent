package com.daiba.place.model;

/**
 * Created by dolphinzhou on 2016/10/23.
 */
public class Place {
    /**
     * 地址码
     */
    private String code;
    /**
     * 描述
     */
    private String name;
    /**
     * 父地址码
     */
    private String parentId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Place(String code, String name, String parentId) {
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }

    public Place() {
    }
}
