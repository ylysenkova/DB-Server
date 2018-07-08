package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.entity.SQLDataType;
import com.lysenkova.dbserver.entity.metadata.Column;
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
        schemaRequest.setEntityType(EntityType.SCHEMA);
        schemaRequest.setEntityName("soap");
        create.createSchema(schemaRequest);
        // Create table
        Request tableRequest = new Request();
        Map<String, String> tableHeader = new HashMap<>();
        tableHeader.put("type", "CREATE");
        tableHeader.put("schema", "soap");
        tableRequest.setHeader(tableHeader);
        tableRequest.setEntityType(EntityType.getByName("table"));
        tableRequest.setEntityName("test");
        List<Column> columnsMeta = new ArrayList<>();
        columnsMeta.add(new Column("id", SQLDataType.getByName("integer")));
        columnsMeta.add(new Column("name", SQLDataType.getByName("varchar")));
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
        requestOne.setEntityType(EntityType.getByName("table"));
        requestOne.setEntityName("test");
        List<List<Column>> data = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", SQLDataType.getByName("integer"), "22"));
        columns.add(new Column("name", SQLDataType.getByName("varchar"), "Mark"));
        data.add(columns);
        requestOne.setData(data);
        requests.add(requestOne);
        Request requestTwo = new Request();
        Map<String, String> headerTwo = new HashMap<>();
        headerTwo.put("type", "INSERT");
        headerTwo.put("schema", "soap");
        requestTwo.setHeader(header);
        requestTwo.setEntityType(EntityType.TABLE);
        requestTwo.setEntityName("tost");
        List<List<Column>> dataTwo = new ArrayList<>();
        List<Column> columnsTwo = new ArrayList<>();
        columnsTwo.add(new Column("id", SQLDataType.getByName("integer"), "23"));
        columnsTwo.add(new Column("name", SQLDataType.getByName("varchar"), "Stephan"));
        dataTwo.add(columns);
        requestTwo.setData(dataTwo);
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
        request.setEntityType(EntityType.TABLE);
        request.setEntityName("test");
        List<List<Column>> data = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", null, "22"));
        data.add(columns);
        request.setData(data);
        SelectQueryExecutor selectQueryExecutor = new SelectQueryExecutor("src\\test\\resources\\db\\");
        Response expected = new Response();
        expected.setMessage("SELECT query executed successfully.");
        expected.setEntityType(EntityType.TABLE);
        expected.setEntityName(request.getEntityName());
        expected.setData(data);
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