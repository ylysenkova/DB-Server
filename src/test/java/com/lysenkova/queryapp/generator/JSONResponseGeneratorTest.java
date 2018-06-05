package com.lysenkova.queryapp.generator;

import com.lysenkova.queryapp.entity.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONResponseGeneratorTest {

    @Test
    public void createResponse() {
        Response response = new Response();
        response.setMessage("Schema created successfully");
        response.setEntity("schema");
        response.setEntityName("soap");

        JSONResponseGenerator generator = new JSONResponseGenerator();
        String expected = "{\"message\":\"Schema created successfully\",\"entity\":\"schema\",\"entityName\":\"soap\"}";
        String actual = generator.createResponse(response);
        assertEquals(expected, actual);

    }
}