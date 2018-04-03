package com.google.code.or;

/**
 * @Author: wfeng
 * @Date: 2018/4/3
 */
public class ColumnInfo {

    private String name;
    private String type;

    public ColumnInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
