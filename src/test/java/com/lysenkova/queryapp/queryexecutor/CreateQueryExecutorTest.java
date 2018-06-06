package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.SQLType;
import com.lysenkova.queryapp.entity.metadata.Column;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CreateQueryExecutorTest {
    @Before
    public void before() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLType.valueOf("CREATE").getName());
        request.setHeader(header);
        request.setEntity("schema");
        request.setName("soap");
        File file = new File("src\\test\\resources\\db\\metadata\\soap");
        file.mkdir();
    }

    @Test
    public void createSchema() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLType.valueOf("CREATE").getName());
        request.setHeader(header);
        request.setEntity("schema");
        request.setName("soup");
        CreateQueryExecutor queryExecutor = new CreateQueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.createSchema(request);
        File file = new File("src\\test\\resources\\db\\" + request.getName() + ".xml");
        assertEquals("soup.xml", file.getName());
    }

    @Test
    public void createTable() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", "BIGINT"));
        columns.add(new Column("name", "VARCHAR(255)"));

        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLType.valueOf("CREATE").getName());
        header.put("schema", "soap");
        request.setHeader(header);
        request.setEntity("table");
        request.setName("soap");
        request.setColumns(columns);
        CreateQueryExecutor queryExecutor = new CreateQueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.createTable(request);
        File file = new File("src\\test\\resources\\db\\soap\\" + request.getName() + ".xml");
        assertEquals("soap.xml", file.getName());
    }

    @After
    public void clean() {
        File fileTable = new File("src\\test\\resources\\db\\metadata\\soap\\");
        File[] filesTable = fileTable.listFiles();
        for (File file : filesTable) {
             file.delete();
            System.out.println(file.getAbsolutePath());
        }
        File file = new File("src\\test\\resources\\db\\metadata\\");
        File[] files = file.listFiles();
        for (File testFile : files) {
            testFile.delete();
        }
    }
}