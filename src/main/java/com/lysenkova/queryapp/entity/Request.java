package com.lysenkova.queryapp.entity;

import com.lysenkova.queryapp.entity.metadata.Column;

import java.util.List;
import java.util.Map;

public class Request {
    private Map<String, String> header;
    private String entity;
    private String name;
    private List<Column> columns;
    private List<Column> conditions;

    public Request() {
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getConditions() {
        return conditions;
    }

    public void setConditions(List<Column> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", entity='" + entity + '\'' +
                ", name='" + name + '\'' +
                ", columns=" + columns +
                ", conditions=" + conditions +
                '}';
    }
}
