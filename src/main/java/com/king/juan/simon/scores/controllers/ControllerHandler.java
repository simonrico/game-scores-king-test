package com.king.juan.simon.scores.controllers;

import java.io.IOException;

import com.king.juan.simon.scores.server.CustomHttpExchange;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.king.juan.simon.scores.exceptions.BadRequestException;
import com.king.juan.simon.scores.exceptions.MethodNotAllowedException;
import com.king.juan.simon.scores.exceptions.NotFoundException;
import com.king.juan.simon.scores.exceptions.UnauthorizedException;

/**
 * Controller Handler.
 */
public class ControllerHandler implements HttpHandler {
    private final ControllerFactory controllerFactory;

    public ControllerHandler(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        CustomHttpExchange customHttpExchange = new CustomHttpExchange(httpExchange);
        try {
            Controller controller = controllerFactory.getController(customHttpExchange);
            if(controller == null) {
                customHttpExchange.sendResponse(404, "NOT FOUND");
                return;
            }
            customHttpExchange.sendResponse(200, controller.execute(customHttpExchange));
        } catch (Exception e) {
            handleError(customHttpExchange, e);
        }

    }

    /**
     * Exception Handler.
     * @param httpExchange
     * @param e
     * @throws IOException
     */
    private void handleError(CustomHttpExchange httpExchange, Exception e) throws IOException {
        if (e instanceof MethodNotAllowedException) {
            httpExchange.sendResponse(405, e.getMessage());
        } else if (e instanceof BadRequestException) {
            httpExchange.sendResponse(400, e.getMessage());
        } else if (e instanceof NotFoundException) {
            httpExchange.sendResponse(404, e.getMessage());
        } else if (e instanceof UnauthorizedException) {
            httpExchange.sendResponse(401, e.getMessage());
        } else {
            httpExchange.sendResponse(500, "Internal Server Error");
        }
    }
}
