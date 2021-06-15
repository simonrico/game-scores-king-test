package com.king.juan.simon.scores.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.king.juan.simon.scores.controllers.ControllerFactory;
import com.king.juan.simon.scores.controllers.ControllerHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * Score Server Class
 */
public class ScoreServer {


    private HttpServer server;

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        HttpContext context = server.createContext("/", new ControllerHandler(new ControllerFactory()));
        context.setAuthenticator(null);
        server.setExecutor(null);
        server.start();
    }


    public void stop(int delay) {
        if (null != server) {
            server.stop(delay);
        }
    }
}
