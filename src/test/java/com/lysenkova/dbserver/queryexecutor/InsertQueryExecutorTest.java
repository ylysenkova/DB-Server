package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.entity.SQLDataType;
import com.lysenkova.dbserver.entity.metadata.Column;
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
        request.setEntityType(EntityType.TABLE);
        request.setEntityName("test");
        List<List<Column>> data = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", SQLDataType.getByName("integer"), "22"));
        columns.add(new Column("name", SQLDataType.getByName("varchar"), "Mark"));
        List<Column> columnsTwo = new ArrayList<>();
        columnsTwo.add(new Column("id", SQLDataType.getByName("integer"), "23"));
        columnsTwo.add(new Column("name", SQLDataType.getByName("varchar"), "Stephan"));
        data.add(columns);
        data.add(columnsTwo);
        request.setData(data);
        InsertQueryExecutor insertQueryExecutor = new InsertQueryExecutor("src\\test\\resources\\db\\");
        Response expected = new Response();
        expected.setMessage("Data inserted successfully.");
        expected.setEntityType(EntityType.TABLE);
        expected.setEntityName(request.getEntityName());
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