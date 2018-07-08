package com.lysenkova.dbserver.parser.requestparser.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.SQLQueryType;
import com.lysenkova.dbserver.parser.requestparser.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;


public class JSONRequestParser implements RequestParser {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String QUERY_TYPE = "type";

    public Request parse(BufferedReader reader) {
        LOGGER.info("Starting parse CREATE query.");
        Request request;
        String sql;
        try {
            StringBuilder requestBuilder = new StringBuilder();
            while (!(sql = reader.readLine()).equals("end")) {
                requestBuilder.append(sql);
            }
            request = mapper.readValue(requestBuilder.toString(), Request.class);
            SQLQueryType.getByName(request.getHeader().get(QUERY_TYPE));

            LOGGER.info("Request: {} parsed.", request);
        } catch (IOException e) {
            LOGGER.error("Error during reading stream");
            throw new RuntimeException("Error during reading stream", e);
        }
        return request;
    }

}
