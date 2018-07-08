package com.lysenkova.dbserver.generator.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.generator.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONResponseGenerator implements ResponseGenerator {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public String createResponse(Response response) {
        LOGGER.info("Starting create JSON response");
        StringBuilder responseLine = new StringBuilder();
        try {
            responseLine.append(mapper.writeValueAsString(response));
            responseLine.append("\r\n\r\n");

        } catch (JsonProcessingException e) {
            LOGGER.error("Error during creating JSON response.");
            throw new RuntimeException("Error during creating JSON response.", e);
        }
        LOGGER.info("JSON response created: {}", responseLine);
        return responseLine.toString();
    }
}
