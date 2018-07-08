package com.lysenkova.dbserver;

import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.generator.json.JSONResponseGenerator;
import com.lysenkova.dbserver.generator.ResponseGenerator;
import com.lysenkova.dbserver.parser.requestparser.json.JSONRequestParser;
import com.lysenkova.dbserver.parser.requestparser.RequestParser;
import com.lysenkova.dbserver.queryexecutor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RequestHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String path;
    private BufferedReader reader;
    private BufferedWriter writer;
    private RequestParser parser = new JSONRequestParser();


    public void handle() {
        LOGGER.info("Request handle is started.");
        Request request = parser.parse(reader);
        QueryExecutor queryExecutor = new QueryExecutor(path);
        Response response = queryExecutor.executeQuery(request);
        ResponseGenerator responseGenerator = new JSONResponseGenerator();
        String responseLine = responseGenerator.createResponse(response);
        try {
            writer.write(responseLine);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Error during writing response.");
            throw new RuntimeException("Error during writing response.", e);
        }

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
}
