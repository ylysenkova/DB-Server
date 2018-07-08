package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.entity.metadata.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class InsertQueryExecutor {
    private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String path;

    public InsertQueryExecutor(String path) {
        this.path = path;
    }

    protected Response insertData(Request request) {
        LOGGER.info("Insertion data into table: {}", request.getEntityName());

        Response response = new Response();
        checkSchemaExistence(request);
        File tableFile = createTableFile(request);
        writeTableData(request, tableFile);
        response.setMessage("Data inserted successfully.");
        response.setEntityType(request.getEntityType());
        response.setEntityName(request.getEntityName());
        LOGGER.info("Data inserted into table: {} ", tableFile);
        return response;
    }

    private File createTableFile(Request request) {
        String tableSchema = request.getHeader().get("schema");
        String tableName = request.getEntityName();
        File dataFile = new File(path + "data\\" + tableSchema + "\\" + tableName + ".xml");
        try {
            if (!dataFile.exists()) {
                DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element rootElement = document.createElement("table");
                document.appendChild(rootElement);
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(dataFile);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            LOGGER.error("Error during creating table file: {}", dataFile);
            throw new RuntimeException("Error during creating table file.", e);
        }
        return dataFile;
    }

    private void writeTableData(Request request, File file) {
        LOGGER.info("Writing data into table file: {}", file);
        List<List<Column>> data = request.getData();
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element rootElement = document.getDocumentElement();

            for (List<Column> columns : data) {
                Element rowElement = document.createElement("row");
                rootElement.appendChild(rowElement);
                for (Column column : columns) {
                    Element columnElement = document.createElement("column");
                    rowElement.appendChild(columnElement);
                    Element columnNameElement = document.createElement(column.getName());
                    columnNameElement.setAttribute("value", column.getValue());
                    columnElement.appendChild(columnNameElement);
                }
            }

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            LOGGER.error("Error during writing data into table file: {}", file);
            throw new RuntimeException("Error during writing data into table file.", e);
        }
    }

    private void checkSchemaExistence(Request request) {
        String schemaName = request.getHeader().get("schema");
        File schemaDir = new File(path + "data\\" + schemaName);
        if (!schemaDir.exists()) {
            boolean isDirectoryCreated = schemaDir.mkdir();
            if(!isDirectoryCreated) {
                LOGGER.info("Schema: {} was not created.", schemaDir);
            }
        } else {
            LOGGER.info("Schema: {} exists.", schemaName);
        }
    }

}
