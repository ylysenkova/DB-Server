package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.metadata.Column;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InsertQueryExecutorTest {

    @Test
    public void insertData() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", "INSERT");
        header.put("schema", "soup");
        request.setHeader(header);
        request.setEntity("table");
        request.setName("test");
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", "int", "22"));
        columns.add(new Column("name", "varchar(255)", "Mark"));
        request.setColumns(columns);
        InsertQueryExecutor insertQueryExecutor = new InsertQueryExecutor("src\\test\\resources\\db\\");
        Response expected = new Response();
        expected.setMessage("Data inserted successfully.");
        expected.setEntity("table");
        expected.setEntityName(request.getName());
        Response actual = insertQueryExecutor.insertData(request);
        assertEquals(expected, actual);
    }

    @After
    public void clean() {
        File fileTable = new File("src\\test\\resources\\db\\data\\soup\\");
        File[] filesTable = fileTable.listFiles();
        for (File file : filesTable) {
            file.delete();
            System.out.println(file.getAbsolutePath());
        }
        File file = new File("src\\test\\resources\\db\\data\\");
        File[] files = file.listFiles();
        for (File testFile : files) {
            testFile.delete();
        }
    }
}