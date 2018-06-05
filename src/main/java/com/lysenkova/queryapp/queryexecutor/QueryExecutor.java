package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.metadata.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.List;

public class QueryExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String path;
    private Response response;

    public QueryExecutor(String path) {
        this.path = path;
    }

    public Response executeQuery(Request request) {
        LOGGER.info("Starting execution of query.");
        if ("schema".equalsIgnoreCase(request.getEntity())) {
            response = createSchema(request);
        } else if ("table".equalsIgnoreCase(request.getEntity())){
            response = createTable(request);
        } else {
            response = new Response();
            response.setMessage("Wrong type of entity. Should be \"schema\" or \"table\"");
        }

        LOGGER.info("Query executed.");
        return response;
    }

    private Response createSchema(Request request) {
        LOGGER.info("Creating Schema: {}", request.getEntity());
        String schemaFile = request.getName();
        File file = new File(path + schemaFile);
        response = new Response();
        if (!file.exists()) {
            boolean isCreated = file.mkdir();
            if (!isCreated) {
                LOGGER.info("Schema {} was not created.", schemaFile);
            }
            response.setMessage("Schema successfully created");
            response.setEntity(request.getEntity());
            response.setEntityName(schemaFile);
        } else {
            LOGGER.error("Schema: {} has already exist.", schemaFile);
            response.setMessage("Schema has already exist. Please use another name for schema");
        }


        LOGGER.info("Schema: {} created.", schemaFile);
        return response;
    }

    private Response createTable(Request request) {
        LOGGER.info("Creating Table: {}", request.getName());
        String tableSchema = request.getHeader().get("schema");
        String tableFile = request.getName();
        File file = new File(path + tableSchema + "\\" + tableFile + ".xml");
        try {
            boolean isCreated = file.createNewFile();
            writeTableMetadata(request, file);
            if (!isCreated) {
                LOGGER.info("Table {} was not created.", tableFile);
            }
        } catch (IOException e) {
            LOGGER.error("Error during creating table file: {}", tableFile);
            throw new RuntimeException("Error during creating table file.", e);
        }
        response = new Response();
        response.setMessage("Table successfully created.");
        response.setEntity(request.getEntity());
        response.setEntityName(tableFile);
        LOGGER.info("Table: {} created.", tableFile);
        return response;
    }

    private void writeTableMetadata(Request request, File file) {
        LOGGER.info("Writing columns into table file: {}", file);
        List<Column> columns = request.getColumns();
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            FileOutputStream stream = new FileOutputStream(file);
            XMLStreamWriter writer = factory.createXMLStreamWriter(stream);
            writer.writeStartDocument();
            writer.writeStartElement("columns");
            for (Column column : columns) {
                writer.writeStartElement("column");
                writer.writeAttribute("name", column.getName());
                writer.writeStartElement("type");
                writer.writeCharacters(column.getDataType());
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();
            stream.close();
        } catch (Exception e) {
            LOGGER.error("Error during writing into table file: {}", file);
            throw new RuntimeException("Error during writing into table file.", e);
        }
        LOGGER.info("Columns wrote into table file: {}", file);
    }
}
