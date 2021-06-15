package com.king.juan.simon.scores.controllers;

import java.io.IOException;

import com.king.juan.simon.scores.server.CustomHttpExchange;

/**
 * Controller Contract.
 */
public interface Controller {

    /**
     * Method that executes the logic based on the given request.
     * @param httpExchange request from client.
     * @return
     * @throws IOException
     */
    String execute(CustomHttpExchange httpExchange) throws IOException;
}
