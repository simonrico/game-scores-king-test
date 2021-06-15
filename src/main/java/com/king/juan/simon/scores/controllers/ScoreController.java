package com.king.juan.simon.scores.controllers;

import java.io.IOException;

import com.king.juan.simon.scores.model.Score;
import com.king.juan.simon.scores.model.Session;
import com.king.juan.simon.scores.persistance.ScoreRepository;
import com.king.juan.simon.scores.persistance.SessionRepository;
import com.king.juan.simon.scores.persistance.UserRepository;
import com.king.juan.simon.scores.server.CustomHttpExchange;

import com.king.juan.simon.scores.exceptions.MethodNotAllowedException;
import com.king.juan.simon.scores.exceptions.UnauthorizedException;

public class ScoreController implements Controller {

    public static String PATH = "\\/(\\d+)\\/score";

    private ScoreRepository scoreRepository;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    ScoreController(ScoreRepository scoreRepository, SessionRepository sessionRepository,
            UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override public String execute(CustomHttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if ("POST".equals(method)) {
            return addScore(httpExchange);
        }
        throw new MethodNotAllowedException(String.format("%s Method Not Allowed", method));
    }

    private String addScore(CustomHttpExchange httpExchange) throws IOException {
        Integer score = httpExchange.getRequestBodyAsInt();
        Integer level = httpExchange.getPathVariable(PATH, 1);
        String sessionKey = httpExchange.getParameter("sessionkey");
        Session session = sessionRepository.getSession(sessionKey);
        if (session == null) {
            throw new UnauthorizedException(String.format("Unauthorized invalid session key %s", sessionKey));
        }
        Score oldScore = scoreRepository.getScoreByLevelAndUser(level, session.getUserId());
        Score newScore = new Score(userRepository.getUser(session.getUserId()), score);
        if (oldScore == null || oldScore.compareTo(newScore) < 0) {
            scoreRepository.addNewScore(level, new Score(userRepository.getUser(session.getUserId()), score));
        }
        return "";
    }

}
