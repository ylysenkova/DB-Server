package com.lysenkova.dbserver.entity;

public enum SQLQueryType {
    CREATE("CREATE"), INSERT("INSERT"), DELETE("DELETE"), UPDATE("UPDATE"), SELECT("SELECT");

    private String name;

    SQLQueryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SQLQueryType getByName(String name) {
        SQLQueryType[] values = values();
        for (SQLQueryType sqlMethod : values) {
            if(sqlMethod.getName().equalsIgnoreCase(name)) {
                return sqlMethod;
            }
        }
        throw new IllegalArgumentException("Incorrect SQL method name: " + name);
    }
}
