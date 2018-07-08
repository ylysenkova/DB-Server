package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.SQLDataType;
import com.lysenkova.dbserver.entity.SQLQueryType;
import com.lysenkova.dbserver.entity.metadata.Column;
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
        header.put("type", SQLQueryType.valueOf("CREATE").getName());
        request.setHeader(header);
        request.setEntityType(EntityType.SCHEMA);
        request.setEntityName("soap");
        File file = new File("src\\test\\resources\\db\\metadata\\soap");
        file.mkdir();
    }

    @Test
    public void createSchema() {
        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLQueryType.valueOf("CREATE").getName());
        request.setHeader(header);
        request.setEntityType(EntityType.SCHEMA);
        request.setEntityName("soup");
        CreateQueryExecutor queryExecutor = new CreateQueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.createSchema(request);
        File file = new File("src\\test\\resources\\db\\" + request.getEntityName());
        assertEquals("soup", file.getName());
    }

    @Test
    public void createTable() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", SQLDataType.getByName("BIGINT")));
        columns.add(new Column("name", SQLDataType.getByName("VARCHAR")));

        Request request = new Request();
        Map<String, String> header = new HashMap<>();
        header.put("type", SQLQueryType.valueOf("CREATE").getName());
        header.put("schema", "soup");
        request.setHeader(header);
        request.setEntityType(EntityType.TABLE);
        request.setEntityName("test");
        request.setColumns(columns);
        CreateQueryExecutor queryExecutor = new CreateQueryExecutor("src\\test\\resources\\db\\");
        queryExecutor.createTable(request);
        File file = new File("src\\test\\resources\\db\\" + request.getEntityName() + ".xml");
        assertEquals("test.xml", file.getName());
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