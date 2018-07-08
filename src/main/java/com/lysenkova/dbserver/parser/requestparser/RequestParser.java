package com.lysenkova.dbserver.parser.requestparser;


import com.lysenkova.dbserver.entity.Request;

import java.io.BufferedReader;

public interface RequestParser {
    Request parse(BufferedReader reader);
}
