package com.lysenkova.dbserver.entity.metadata;

import com.lysenkova.dbserver.entity.SQLDataType;

import java.util.Objects;

public class Column {
    private String name;
    private SQLDataType sqlDataType;
    private String value;

    public Column() {
    }

    public Column(String name, SQLDataType sqlDataType) {
        this.name = name;
        this.sqlDataType = sqlDataType;
    }

    public Column(String name, SQLDataType sqlDataType, String value) {
        this.name = name;
        this.sqlDataType = sqlDataType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SQLDataType getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(SQLDataType sqlDataType) {
        this.sqlDataType = sqlDataType;
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
                getSqlDataType() == column.getSqlDataType() &&
                Objects.equals(getValue(), column.getValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getSqlDataType(), getValue());
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", sqlDataType=" + sqlDataType +
                ", value='" + value + '\'' +
                '}';
    }
}
