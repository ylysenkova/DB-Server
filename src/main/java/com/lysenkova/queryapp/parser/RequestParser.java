package com.lysenkova.queryapp.parser;


import com.lysenkova.queryapp.entity.Request;

import java.io.BufferedReader;

public interface RequestParser {
    Request parse(BufferedReader reader);
}
