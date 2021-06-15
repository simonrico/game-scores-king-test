package com.king.juan.simon.scores.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.king.juan.simon.scores.exceptions.NotFoundException;
import com.king.juan.simon.scores.persistance.RepositoryFactory;
import com.king.juan.simon.scores.server.CustomHttpExchange;
import com.sun.net.httpserver.HttpExchange;

/**
 * Factory that constructs instances of the controllers based on the request path.
 */
public class ControllerFactory {

    private final Map<String, Controller> controllerMap;
    private final RepositoryFactory repositoryFactory = new RepositoryFactory();

    public ControllerFactory() {

        controllerMap = new HashMap<String, Controller>() {{
            put(LoginController.PATH, new LoginController(repositoryFactory.getUserRepository(),
                    repositoryFactory.getSessionRepository()));
            put(ScoreController.PATH, new ScoreController(repositoryFactory.getScoreRepository(),
                    repositoryFactory.getSessionRepository(), repositoryFactory.getUserRepository()));
            put(HighScoreController.PATH, new HighScoreController(repositoryFactory.getScoreRepository()));
        }};
    }

    public Controller getController(CustomHttpExchange httpExchange) {
        for (Map.Entry<String, Controller> e : controllerMap.entrySet()) {
            if (Pattern.matches(e.getKey(), httpExchange.getRequestPath())) {
                return e.getValue();
            }
        }
        throw new NotFoundException("Not found");
    }
}
