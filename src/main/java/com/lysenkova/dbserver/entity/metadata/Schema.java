package com.lysenkova.dbserver.entity.metadata;

public class Schema {
    private String name;

    public Schema() {
    }

    public Schema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name  +
                '}';
    }
}
