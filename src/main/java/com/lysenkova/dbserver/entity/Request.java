package com.lysenkova.dbserver.entity;

import com.lysenkova.dbserver.entity.metadata.Column;

import java.util.List;
import java.util.Map;

public class Request {
    private Map<String, String> header;
    private EntityType entityType;
    private String entityName;
    private List<Column> columns;
    private List<List<Column>> data;

    public Request() {
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<List<Column>> getData() {
        return data;
    }

    public void setData(List<List<Column>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", entityType='" + entityType + '\'' +
                ", entityName='" + entityName + '\'' +
                ", columns=" + columns +
                ", data=" + data +
                '}';
    }
}
