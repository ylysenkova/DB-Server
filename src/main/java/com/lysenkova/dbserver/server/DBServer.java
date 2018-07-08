package com.lysenkova.dbserver.server;

import com.lysenkova.dbserver.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private int port;
    private String dbPath;

    public static void main(String[] args) {
        DBServer server = new DBServer();
        server.setPort(3000);
        server.setDbPath("src\\main\\resources\\db\\");
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    RequestHandler handler = new RequestHandler();
                    handler.setPath(dbPath);
                    handler.setReader(reader);
                    handler.setWriter(writer);
                    handler.handle();
                } catch (IOException e) {
                    throw new RuntimeException("Error during opening/closing stream.", e);
                }

            }
        } catch (IOException e) {
            LOGGER.error("Error during opening a socket", e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
}
