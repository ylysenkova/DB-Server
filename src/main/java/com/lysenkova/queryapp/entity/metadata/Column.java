package com.lysenkova.queryapp.entity.metadata;

import java.util.Objects;

public class Column {
    private String name;
    private String dataType;
    private String value;

    public Column() {
    }

    public Column(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }


    public Column(String name, String dataType, String value) {
        this.name = name;
        this.dataType = dataType;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        Column column = (Column) o;
        return Objects.equals(getName(), column.getName()) &&
                Objects.equals(getDataType(), column.getDataType()) &&
                Objects.equals(getValue(), column.getValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDataType(), getValue());
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
