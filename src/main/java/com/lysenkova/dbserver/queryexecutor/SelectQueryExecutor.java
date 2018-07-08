package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.entity.metadata.Column;
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
    private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String path;

    public SelectQueryExecutor(String path) {
        this.path = path;
    }

    public Response selectData(Request request) {
        LOGGER.info("Execution of SELECT query is started.");
        Response response = new Response();
        String schema = request.getHeader().get("schema");
        String table = request.getEntityName();
        File tablePath = new File(path + "data\\" + schema + "\\" + table + ".xml");
        if (tablePath.exists()) {
            List<List<Column>>  selectedResult = selectColumns(request, tablePath);
            response.setMessage("SELECT query executed successfully.");
            response.setEntityType(request.getEntityType());
            response.setEntityName(table);
            response.setData(selectedResult);

        } else {
            LOGGER.info("Table: {} does not exist.", table);
            response.setMessage("Table: {} does npt exist. Please check the correctness of table name.");
        }
        return response;
    }

    private List<List<Column>> selectColumns(Request request, File file) {
        List<List<Column>> data = request.getData();
        List<List<Column>> selectedResult = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList rowElement = rootElement.getElementsByTagName("columns");
            for (int i = 0; i < rowElement.getLength(); i++) {
                for (List<Column> columnList : data) {
                    for (Column column : columnList) {
                        Node columnTag = rowElement.item(i).getChildNodes().item(i);
                        String columnName = columnTag.getNodeName();
                        String columnValue = ((DeferredElementImpl) columnTag).getAttribute("value");
                        if (column.getName().equalsIgnoreCase(columnName) &&
                                column.getValue().equalsIgnoreCase(columnValue)) {
                            columns.add(new Column(columnName, null, columnValue));
                        }
                    }
                    selectedResult.add(columns);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during reading file: {}", file);
            throw new RuntimeException("Error during reading file", e);
        }
        return selectedResult;
    }
}
