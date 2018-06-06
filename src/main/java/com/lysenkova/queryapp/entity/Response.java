package com.lysenkova.queryapp.entity;

import java.util.Objects;

public class Response {
    private String message;
    private String entity;
    private String entityName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;
        Response response = (Response) o;
        return Objects.equals(getMessage(), response.getMessage()) &&
                Objects.equals(getEntity(), response.getEntity()) &&
                Objects.equals(getEntityName(), response.getEntityName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMessage(), getEntity(), getEntityName());
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", entity='" + entity + '\'' +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
