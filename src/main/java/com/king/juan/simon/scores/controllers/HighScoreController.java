package com.king.juan.simon.scores.controllers;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.king.juan.simon.scores.persistance.ScoreRepository;
import com.king.juan.simon.scores.server.CustomHttpExchange;

import com.king.juan.simon.scores.exceptions.MethodNotAllowedException;

public class HighScoreController implements Controller {

    protected static String PATH = "\\/(\\d+)\\/highscorelist";

    private ScoreRepository scoreRepository;

    public HighScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public String execute(CustomHttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();
        if ("GET".equals(method)) {
            return getHighScoreList(httpExchange);
        }
        throw new MethodNotAllowedException(String.format("%s Method Not Allowed", method));
    }

    private String getHighScoreList(CustomHttpExchange httpExchange) {
        Integer level = httpExchange.getPathVariable(PATH, 1);
        return scoreRepository.getScoresByLevel(level).stream().sorted(Comparator.reverseOrder()).limit(15)
                .map(s -> s.getUser().getUserID() + "=" + s.getScore()).collect(
                        Collectors.joining(","));
    }

}
