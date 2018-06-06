package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
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

import static org.junit.Assert.*;

public class QueryExecutorTest {

    @Test
    public void executeQuerySchemaTest() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLType.valueOf("CREATE").getName());
        request.setHeader(header);
        request.setEntity("schema");
        request.setName("soup");
        QueryExecutor queryExecutor = new QueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.executeQuery(request);
        File file = new File("src\\test\\resources\\db\\" + request.getName());
        assertEquals("soup", file.getName());
    }

    @Test
    public void executeQueryTableTest() {
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
        QueryExecutor queryExecutor = new QueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.executeQuery(request);
        File file = new File("src\\test\\resources\\db\\soap\\" + request.getName() + ".xml");
        assertEquals("soap.xml", file.getName());
    }

    @Test
    public void executeQueryInsertTest() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", "INSERT");
        header.put("schema", "soap");
        request.setHeader(header);
        request.setEntity("table");
        request.setName("test");
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", "int", "22"));
        columns.add(new Column("name", "varchar(255)", "Mark"));
        request.setColumns(columns);
        QueryExecutor queryExecutor = new QueryExecutor("src\\test\\resources\\db\\");
        Response expected = new Response();
        expected.setMessage("Data inserted successfully.");
        expected.setEntity("table");
        expected.setEntityName(request.getName());
        Response actual = queryExecutor.executeQuery(request);
        assertEquals(expected, actual);
    }

    @After
    public void clean() {
        File fileTable = new File("src\\test\\resources\\db\\data\\soap\\");
        File[] filesTable = fileTable.listFiles();
        if (filesTable != null) {
            for (File file : filesTable) {
                file.delete();
                System.out.println(file.getAbsolutePath());
            }
        }
        File file = new File("src\\test\\resources\\db\\data\\");
        File[] files = file.listFiles();
        for (File testFile : files) {
            testFile.delete();
        }
        File fileTableMeta = new File("src\\test\\resources\\db\\metadata\\soap\\");
        File[] filesTableMeta = fileTableMeta.listFiles();
        if (filesTableMeta != null) {
            for (File fileMeta : filesTableMeta) {
                fileMeta.delete();
                System.out.println(fileMeta.getAbsolutePath());
            }
        }
        File fileMeta = new File("src\\test\\resources\\db\\metadata\\");
        File[] filesMeta = fileMeta.listFiles();
        for (File testFile : filesMeta) {
            testFile.delete();
        }
    }
}