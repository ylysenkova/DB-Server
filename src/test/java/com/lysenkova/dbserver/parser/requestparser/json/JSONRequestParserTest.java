package com.lysenkova.dbserver.parser.requestparser.json;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.SQLDataType;
import com.lysenkova.dbserver.entity.metadata.Column;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JSONRequestParserTest {
    private JSONRequestParser parser = new JSONRequestParser();

    @Test
    public void parseCreateQueryForSchema() {
        InputStream inputStream = new ByteArrayInputStream("{\"header\":{\"type\":\"create\"},\"entityType\":\"SCHEMA\",\"entityName\":\"soap\",\"columns\":null,\"data\":null}\r\nend".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parse(reader);
        String expectedName = "soap";
        String actualName = request.getEntityName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void parseCreateQueryForTable() {
        InputStream inputStream = new ByteArrayInputStream(("{\"header\":{\"type\":\"create\"},\"entityType\":\"TABLE\",\"entityName\":\"soap\"," +
                "\"columns\":[{\"name\" :\"id\", \"sqlDataType\" :\"BIGINT\"}, " +
                "{\"name\" :\"name\", \"sqlDataType\" :\"VARCHAR\"}]},\"data\":null}\r\nend").getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parse(reader);
        String expectedTable = "soap";
        String actualTable = request.getEntityName();
        List<Column> expectedColumns = new ArrayList<>();
        expectedColumns.add(new Column("id", SQLDataType.getByName("BIGINT")));
        expectedColumns.add(new Column("name", SQLDataType.getByName("VARCHAR")));
        List<Column> actualColumns = request.getColumns();
        assertEquals(expectedTable, actualTable);
        assertEquals(expectedColumns, actualColumns);
    }

    @Test
    public void parseTest() {
        InputStream inputStream = new ByteArrayInputStream(("{\"header\":{\"type\":\"create\"},\"entityType\":\"TABLE\",\"entityName\":\"soap\"," +
                "\"columns\":[{\"name\" :\"id\", \"sqlDataType\" :\"BIGINT\"}, " +
                "{\"name\" :\"name\", \"sqlDataType\" :\"VARCHAR\"}]},\"data\":null}\r\nend").getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parse(reader);
        EntityType expectedEntity = EntityType.getByName("table");
        EntityType actualEntity = request.getEntityType();
        String expectedName = "soap";
        String actualName = request.getEntityName();
        assertEquals(expectedEntity, actualEntity);
        assertEquals(expectedName, actualName);
    }
}