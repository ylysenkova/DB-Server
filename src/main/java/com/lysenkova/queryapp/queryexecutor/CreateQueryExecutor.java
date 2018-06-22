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

public class CreateQueryExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String path;
    private Response response;

    public CreateQueryExecutor(String path) {
        this.path = path;
    }

    protected Response createSchema(Request request) {
        LOGGER.info("Creating Schema: {}", request.getEntity());
        String schemaFile = request.getName();
        File metadataPath = new File(path + "metadata\\" + schemaFile);
        File dataPath = new File(path + "data\\" + schemaFile);
        response = new Response();
        if (!metadataPath.exists()) {
            boolean isCreated = metadataPath.mkdir();
            if (!isCreated) {
                LOGGER.info("Schema {} was not created in metadata directory.", schemaFile);
            }
            response.setMessage("Schema successfully created");
            response.setEntity(request.getEntity());
            response.setEntityName(schemaFile);
        } else {
            LOGGER.error("Schema: {} has already exist.", schemaFile);
            response.setMessage("Schema has already exist. Please use another name for schema");
        }
        if (!dataPath.exists()) {
            boolean isCreated = dataPath.mkdir();
            if (!isCreated) {
                LOGGER.info("Schema {} was not created in data directory.", schemaFile);
            }
        }
        LOGGER.info("Schema: {} created.", schemaFile);
        return response;
    }

    protected Response createTable(Request request) {
        LOGGER.info("Creating Table: {}", request.getName());
        String tableSchema = request.getHeader().get("schema");
        String tableFile = request.getName();
        File file = new File(path + "metadata\\" + tableSchema + "\\" + tableFile + ".xml");
        checkSchemaExistence(request);
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
        XMLStreamWriter writer = null;
        FileOutputStream stream = null;
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
             stream = new FileOutputStream(file);
             writer = factory.createXMLStreamWriter(stream);
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


        } catch (Exception e) {
            LOGGER.error("Error during writing into table file: {}", file);
            throw new RuntimeException("Error during writing into table file.", e);
        } finally {
            try {
                writer.flush();
                writer.close();
                stream.close();
            } catch (Exception e) {
                LOGGER.error("Error during closing the file: {}", file);
            }
        }
        LOGGER.info("Columns wrote into table file: {}", file);
    }

    private void checkSchemaExistence(Request request) {
        String schemaName = request.getHeader().get("schema");
        File schemaDir = new File(path + "metadata\\" + schemaName);
        if(!schemaDir.exists()) {
            schemaDir.mkdir();
        } else {
            LOGGER.info("Schema: {} exists.", schemaName);
        }
    }
}
