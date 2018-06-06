package com.lysenkova.queryapp.parser.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.SQLType;
import com.lysenkova.queryapp.parser.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;


public class JSONRequestParser implements RequestParser {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Request request;

    public Request parse(BufferedReader reader) {
//        SQLType sqlType = getQueryType(reader);
//        if(SQLType.CREATE == sqlType) {
            request = parseCreateQuery(reader);

        return request;
    }

    Request parseCreateQuery(BufferedReader reader) {
        LOGGER.info("Starting parse CREATE query.");
        String sql;
        try {
            StringBuilder requestBuilder = new StringBuilder();
            while (!(sql = reader.readLine()).equals("end")) {
                requestBuilder.append(sql);
            }
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(requestBuilder.toString(), Request.class);

            LOGGER.info("Request: {} parsed.", request);
        } catch (IOException e) {
            LOGGER.error("Error during reading stream");
            throw new RuntimeException("Error during reading stream", e);
        }
        return request;
    }

//
//    private SQLType getQueryType(BufferedReader reader) {
//        String queryType;
//        try {
//            queryType = reader.readLine();
//            validateSqlType(queryType);
//        } catch (IOException e) {
//            LOGGER.info("Error during reading query type.");
//            throw new RuntimeException("Error during reading query type.", e);
//        }
//        return SQLType.valueOf(queryType);
//    }

    private void validateSqlType(String sqlType) {
        try {
            SQLType.valueOf(sqlType);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid SQL type: {}", sqlType);
        }
    }
}
