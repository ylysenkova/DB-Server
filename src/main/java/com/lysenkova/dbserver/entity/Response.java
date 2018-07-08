package com.lysenkova.dbserver.entity;

import com.lysenkova.dbserver.entity.metadata.Column;

import java.util.List;
import java.util.Objects;

public class Response {
    private String message;
    private EntityType entityType;
    private String entityName;
    private List<Column> columns;
    private List<List<Column>> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;
        Response response = (Response) o;
        return Objects.equals(getMessage(), response.getMessage()) &&
                getEntityType() == response.getEntityType() &&
                Objects.equals(getEntityName(), response.getEntityName()) &&
                Objects.equals(getColumns(), response.getColumns()) &&
                Objects.equals(getData(), response.getData());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMessage(), getEntityType(), getEntityName(), getColumns(), getData());
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", entityType=" + entityType +
                ", entityName='" + entityName + '\'' +
                ", columns=" + columns +
                ", data=" + data +
                '}';
    }
}
