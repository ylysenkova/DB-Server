package com.lysenkova.queryapp.parser.requestparser.json;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.metadata.Column;
import com.lysenkova.queryapp.parser.requestparser.json.JSONRequestParser;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JSONRequestParserTest {

    @Test
    public void parseCreateQueryForSchema()  {
        JSONRequestParser parser = new JSONRequestParser();
        InputStream inputStream = new ByteArrayInputStream("{\"header\":{\"type\":\"create\"},\"entity\":\"schema\",\"name\":\"soap\",\"columns\":null}\r\nend".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parseCreateQuery(reader);
        String expectedName = "soap";
        String actualName = request.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void parseCreateQueryForTable() {
        JSONRequestParser parser = new JSONRequestParser();
        InputStream inputStream = new ByteArrayInputStream(("{\"header\":{\"type\":\"create\"},\"entity\":\"table\",\"name\":\"soap\"," +
                "\"columns\":[{\"name\" :\"id\", \"dataType\" :\"BIGINT\"}, " +
                "{\"name\" :\"name\", \"dataType\" :\"VARCHAR(255)\"}]}\r\nend").getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parseCreateQuery(reader);
        String expectedTable = "soap";
        String actualTable = request.getName();
        List<Column> expectedColumns = new ArrayList<>();
        expectedColumns.add(new Column("id", "BIGINT"));
        expectedColumns.add(new Column("name", "VARCHAR(255)"));
        List<Column> actualColumns = request.getColumns();
        assertEquals(expectedTable, actualTable);
        assertEquals(expectedColumns, actualColumns);
    }

    @Test
    public void parseTest() {
        JSONRequestParser parser = new JSONRequestParser();
        InputStream inputStream = new ByteArrayInputStream(("{\"header\":{\"type\":\"create\"},\"entity\":\"table\",\"name\":\"soap\"," +
                "\"columns\":[{\"name\" :\"id\", \"dataType\" :\"BIGINT\"}, " +
                "{\"name\" :\"name\", \"dataType\" :\"VARCHAR(255)\"}]}\r\nend").getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = parser.parse(reader);
        String expectedEntity = "table";
        String actualEntity = request.getEntity();
        String expectedName = "soap";
        String actualName = request.getName();
        assertEquals(expectedEntity, actualEntity);
        assertEquals(expectedName, actualName);
    }
}