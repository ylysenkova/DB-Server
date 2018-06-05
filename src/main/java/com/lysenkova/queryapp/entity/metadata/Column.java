package com.lysenkova.queryapp.entity.metadata;

import java.util.Objects;

public class Column {
    private String name;
    private String dataType;

    public Column() {
    }

    public Column(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        Column column = (Column) o;
        return Objects.equals(getName(), column.getName()) &&
                Objects.equals(getDataType(), column.getDataType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDataType());
    }
}
