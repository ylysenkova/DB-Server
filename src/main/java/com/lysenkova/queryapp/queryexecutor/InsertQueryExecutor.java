package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.metadata.Column;
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
import java.util.ArrayList;
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

        checkSchemaExistence(request);
        File tableFile = createTableFile(request);
        writeTableData(request, tableFile);
        response = new Response();
        response.setMessage("Data inserted successfully.");
        response.setEntity(request.getEntity());
        response.setEntityName(request.getName());
        LOGGER.info("Data inserted into table: {} ", tableFile);
        return response;
    }

    private File createTableFile(Request request) {
        String tableSchema = request.getHeader().get("schema");
        String tableName = request.getName();
        File dataFile = new File(path + "data\\" + tableSchema + "\\" + tableName + ".xml");
        try {
            if (!dataFile.exists()) {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element rootElement = document.createElement("table");
                document.appendChild(rootElement);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
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
        List<Column> columns = request.getColumns();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element rootElement = document.getDocumentElement();

            Element rowElement = document.createElement("columns");
            rootElement.appendChild(rowElement);

            List<Element> columnElements = new ArrayList<>();
            for (Column column : columns) {
                Element columnElement = document.createElement(column.getName());
                columnElement.setAttribute("value", column.getValue());
                columnElements.add(columnElement);
                rowElement.appendChild(columnElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
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
            schemaDir.mkdir();
        } else {
            LOGGER.info("Schema: {} exists.", schemaName);
        }
    }

}
