package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
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

public class SelectQueryExecutorTest {
    @Before
    public void before() {
        //Create schema
        CreateQueryExecutor create = new CreateQueryExecutor("src\\test\\resources\\db\\");
        Request schemaRequest = new Request();
        Map<String, String> schemaHeader = new HashMap<>();
        schemaHeader.put("type", "CREATE");
        schemaHeader.put("schema", "soap");
        schemaRequest.setHeader(schemaHeader);
        schemaRequest.setEntity("schema");
        schemaRequest.setName("soap");
        create.createSchema(schemaRequest);
        // Create table
        Request tableRequest = new Request();
        Map<String, String> tableHeader = new HashMap<>();
        tableHeader.put("type", "CREATE");
        tableHeader.put("schema", "soap");
        tableRequest.setHeader(tableHeader);
        tableRequest.setEntity("table");
        tableRequest.setName("test");
        List<Column> columnsMeta = new ArrayList<>();
        columnsMeta.add(new Column("id", "int"));
        columnsMeta.add(new Column("name", "varchar(255)"));
        tableRequest.setColumns(columnsMeta);
        create.createTable(tableRequest);
        // Insert data into table
        InsertQueryExecutor executor = new InsertQueryExecutor("src\\test\\resources\\db\\");
        List<Request> requests = new ArrayList<>();
        Request requestOne = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", "INSERT");
        header.put("schema", "soap");
        requestOne.setHeader(header);
        requestOne.setEntity("table");
        requestOne.setName("test");
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", "int", "22"));
        columns.add(new Column("name", "varchar(255)", "Mark"));
        requestOne.setColumns(columns);
        requests.add(requestOne);
        Request requestTwo = new Request();
        Map<String, String> headerTwo = new HashMap<>();
        headerTwo.put("type", "INSERT");
        headerTwo.put("schema", "soap");
        requestTwo.setHeader(header);
        requestTwo.setEntity("table");
        requestTwo.setName("tost");
        List<Column> columnsTwo = new ArrayList<>();
        columnsTwo.add(new Column("id", "int", "23"));
        columnsTwo.add(new Column("name", "varchar(255)", "Stephan"));
        requestTwo.setColumns(columnsTwo);
        requests.add(requestTwo);
        for (Request request : requests) {
            executor.insertData(request);
        }

    }

    @Test
    public void selectData() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", "SELECT");
        header.put("schema", "soap");
        request.setHeader(header);
        request.setEntity("table");
        request.setName("test");
        Column column1 = new Column("id", null, "22");
        List<Column> columns = new ArrayList<>();
        columns.add(column1);
        request.setConditions(columns);
        SelectQueryExecutor selectQueryExecutor = new SelectQueryExecutor("src\\test\\resources\\db\\");
        Response expected = new Response();
        expected.setMessage("SELECT query executed successfully.");
        expected.setEntity("table");
        expected.setEntityName(request.getName());
        expected.setSelectResult(columns);
        Response actual = selectQueryExecutor.selectData(request);
        assertEquals(expected, actual);
    }

    @After
    public void clean() {
        File fileTable = new File("src\\test\\resources\\db\\data\\soap\\");
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