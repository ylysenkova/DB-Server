package com.lysenkova.queryapp.entity;

public enum SQLType {
    CREATE("CREATE"), INSERT("INSERT"), DELETE("DELETE"), UPDATE("UPDATE"), SELECT("SELECT");

    private String name;

    SQLType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SQLType getByName(String name) {
        SQLType[] values = values();
        for (SQLType sqlMethod : values) {
            if(sqlMethod.getName().equalsIgnoreCase(name)) {
                return sqlMethod;
            }
        }
        throw new IllegalArgumentException("Incorrect SQL method name: " + name);
    }
}
