package com.lysenkova.queryapp.queryexecutor;

import com.lysenkova.queryapp.entity.Request;
import com.lysenkova.queryapp.entity.Response;
import com.lysenkova.queryapp.entity.SQLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String path;
    private Response response;

    public QueryExecutor(String path) {
        this.path = path;
    }

    public Response executeQuery(Request request) {
        LOGGER.info("Starting execution of query.");
        if (SQLType.CREATE == SQLType.valueOf(request.getHeader().get("type"))) {
            CreateQueryExecutor createExecutor = new CreateQueryExecutor(path);
            if ("schema".equalsIgnoreCase(request.getEntity())) {
                response = createExecutor.createSchema(request);
            } else if ("table".equalsIgnoreCase(request.getEntity())) {
                response = createExecutor.createTable(request);
            } else {
                response = new Response();
                response.setMessage("Wrong type of entity. Should be \"schema\" or \"table\"");
            }
        } else if (SQLType.INSERT == SQLType.valueOf(request.getHeader().get("type"))) {
            InsertQueryExecutor insertExecutor = new InsertQueryExecutor(path);
            response = insertExecutor.insertData(request);
        } else if (SQLType.SELECT == SQLType.valueOf(request.getHeader().get("type"))) {
            SelectQueryExecutor selectExecutor = new SelectQueryExecutor(path);
            response = selectExecutor.selectData(request);
        }

        LOGGER.info("Query executed.");
        return response;
    }


}
