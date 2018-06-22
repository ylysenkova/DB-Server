package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.metadata.Column;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectQueryExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String path;
    private Response response;

    public SelectQueryExecutor(String path) {
        this.path = path;
    }

    public Response selectData(Request request) {
        LOGGER.info("Execution of SELECT query is started.");
        String schema = request.getHeader().get("schema");
        String table = request.getName();
        File tablePath = new File(path + "data\\" + schema + "\\" + table + ".xml");
        if (tablePath.exists()) {
            List<Column> selectedResult = selectColumns(request, tablePath);
            response = new Response();
            response.setMessage("SELECT query executed successfully.");
            response.setEntity(request.getEntity());
            response.setEntityName(table);
            response.setSelectResult(selectedResult);

        } else {
            LOGGER.info("Table: {} does not exist.", table);
            response.setMessage("Table: {} does npt exist. Please check the correctness of table name.");
        }
        return response;
    }

    private List<Column> selectColumns(Request request, File file) {
        List<Column> conditions = request.getConditions();
        List<Column> selectedResult = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList rowElement = rootElement.getElementsByTagName("columns");
            for (int i = 0; i < rowElement.getLength(); i++) {
                for (Column condition : conditions) {
                    Node columnTag = rowElement.item(i).getChildNodes().item(i);
                    String columnName = columnTag.getNodeName();
                    String columnValue = ((DeferredElementImpl) columnTag).getAttribute("value");
                    if (condition.getName().equalsIgnoreCase(columnName) &&
                            condition.getValue().equalsIgnoreCase(columnValue)) {
                        Column column = new Column(columnName, null, columnValue);
                        selectedResult.add(column);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during reading file: {}", file);
            throw new RuntimeException("Error during reading file", e);
        }
        return selectedResult;
    }
}
