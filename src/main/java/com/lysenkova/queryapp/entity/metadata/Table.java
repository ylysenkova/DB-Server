package com.lysenkova.queryapp.entity.metadata;

import java.util.List;

public class Table {
    private String name;
    private List<Column> column;
    private Schema schema;

    public Table() {
    }

    public Table(String name, List<Column> column) {
        this.name = name;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", column=" + column +
                ", schema=" + schema +
                '}';
    }
}
