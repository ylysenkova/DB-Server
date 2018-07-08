package com.lysenkova.dbserver.generator.json;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONResponseGeneratorTest {

    @Test
    public void createResponse() {
        Response response = new Response();
        response.setMessage("Schema created successfully");
        response.setEntityType(EntityType.SCHEMA);
        response.setEntityName("soap");

        JSONResponseGenerator generator = new JSONResponseGenerator();
        String expected = "{\"message\":\"Schema created successfully\",\"entityType\":\"SCHEMA\",\"entityName\":\"soap\",\"columns\":null,\"data\":null}";
        String actual = generator.createResponse(response).trim();
        assertEquals(expected, actual);

    }
}