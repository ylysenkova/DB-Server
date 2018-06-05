package com.lysenkova.queryapp;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.generator.JSONResponseGenerator;
import com.lysenkova.queryapp.generator.ResponseGenerator;
import com.lysenkova.queryapp.parser.JSONRequestParser;
import com.lysenkova.queryapp.parser.RequestParser;
import com.lysenkova.queryapp.queryexecutor.QueryExecutor;
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

    public void handle() {
        LOGGER.info("Request handle is started.");
        RequestParser parser = new JSONRequestParser();
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
