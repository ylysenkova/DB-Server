package com.lysenkova.queryapp.entity;

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
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", entity='" + entity + '\'' +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
