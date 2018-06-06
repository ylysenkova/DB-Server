package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.metadata.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class InsertQueryExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String path;
    private Response response;

    public InsertQueryExecutor(String path) {
        this.path = path;
    }

    protected Response insertData(Request request) {
        LOGGER.info("Insertion data into table: {}", request.getName());
        String tableSchema = request.getHeader().get("schema");
        String tableName = request.getName();
        checkSchemaExistence(request);
        File dataFile = new File(path + "data\\" + tableSchema + "\\" + tableName + ".xml");
        try{
            boolean isCreated = dataFile.createNewFile();
            writeTableData(request, dataFile);
            if(!isCreated) {
                LOGGER.info("Table {} was not created.", dataFile);
            }
        } catch (IOException e) {
            LOGGER.error("Error during creating table file: {}", dataFile);
            throw new RuntimeException("Error during creating table file.", e);
        }
        response = new Response();
        response.setMessage("Data inserted successfully.");
        response.setEntity(request.getEntity());
        response.setEntityName(request.getName());
        LOGGER.info("Data inserted into table: {} ", dataFile);
        return response;
    }

    private void writeTableData(Request request, File file) {
        LOGGER.info("Writing data into table file: {}", file);
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
                writer.writeCharacters(column.getValue());
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();
            stream.close();
        } catch (Exception e) {
            LOGGER.error("Error during writing data into table file: {}", file);
            throw new RuntimeException("Error during writing data into table file.", e);
        }
        LOGGER.info("Data wrote into table file: {}", file);
    }

    private void checkSchemaExistence(Request request) {
        String schemaName = request.getHeader().get("schema");
        File schemaDir = new File(path + "data\\" + schemaName);
        if(!schemaDir.exists()) {
            schemaDir.mkdir();
        } else {
            LOGGER.info("Schema: {} exists.", schemaName);
        }
    }
}
