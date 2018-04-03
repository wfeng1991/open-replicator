package com.google.code.or;

/**
 * @Author: wfeng
 * @Date: 2018/4/3
 */
public class TableInfo {

    private String databaseName;
    private String tableName;
    private String fullName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
