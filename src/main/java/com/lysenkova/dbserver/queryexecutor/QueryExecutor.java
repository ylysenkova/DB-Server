package com.lysenkova.dbserver.queryexecutor;

import com.lysenkova.dbserver.entity.EntityType;
import com.lysenkova.dbserver.entity.Request;
import com.lysenkova.dbserver.entity.Response;
import com.lysenkova.dbserver.entity.SQLQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryExecutor {
    private static final String QUERY_TYPE = "type";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String path;
    private Response response;

    public QueryExecutor(String path) {
        this.path = path;
    }

    public Response executeQuery(Request request) {
        LOGGER.info("Starting execution of query.");
        if (SQLQueryType.CREATE == SQLQueryType.getByName(request.getHeader().get(QUERY_TYPE))) {
            CreateQueryExecutor createExecutor = new CreateQueryExecutor(path);
            if (EntityType.SCHEMA == request.getEntityType()) {
                response = createExecutor.createSchema(request);
            } else if (EntityType.TABLE == request.getEntityType()) {
                response = createExecutor.createTable(request);
            } else {
                response = new Response();
                response.setMessage("Wrong type of entity. Should be \"schema\" or \"table\"");
            }
        } else if (SQLQueryType.INSERT == SQLQueryType.valueOf(request.getHeader().get(QUERY_TYPE))) {
            InsertQueryExecutor insertExecutor = new InsertQueryExecutor(path);
            response = insertExecutor.insertData(request);
        } else if (SQLQueryType.SELECT == SQLQueryType.valueOf(request.getHeader().get(QUERY_TYPE))) {
            SelectQueryExecutor selectExecutor = new SelectQueryExecutor(path);
            response = selectExecutor.selectData(request);
        }

        LOGGER.info("Query executed.");
        return response;
    }


}
