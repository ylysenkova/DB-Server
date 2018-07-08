package com.lysenkova.dbserver.entity;

public enum EntityType {
    SCHEMA("SCHEMA"), TABLE("TABLE");

    private String entityType;

    EntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }

    public static EntityType getByName(String entityType) {
        EntityType[] values = values();
        for (EntityType sqlEntityType : values) {
            if(sqlEntityType.getEntityType().equalsIgnoreCase(entityType)) {
                return sqlEntityType;
            }
        }
        throw new IllegalArgumentException("Incorrect SQL data type: " + entityType);
    }
}
