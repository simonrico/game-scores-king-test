package com.king.juan.simon.scores.controllers;

import java.util.UUID;

import com.king.juan.simon.scores.model.Session;
import com.king.juan.simon.scores.model.User;
import com.king.juan.simon.scores.persistance.SessionRepository;
import com.king.juan.simon.scores.persistance.UserRepository;
import com.king.juan.simon.scores.server.CustomHttpExchange;

import com.king.juan.simon.scores.exceptions.MethodNotAllowedException;

public class LoginController implements Controller {

    public static String PATH = "\\/(\\d+)\\/login";

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    LoginController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public String execute(CustomHttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();
        if ("POST".equals(method)) {
            return login(httpExchange);
        }
        throw new MethodNotAllowedException(String.format("%s Method Not Allowed", method));
    }

    private String login(CustomHttpExchange httpExchange) {
            Integer userId = httpExchange.getPathVariable(PATH, 1);
            User user = userRepository.getUser(userId);
            if (user == null) {
                userRepository.addUser(new User(userId));
                user = userRepository.getUser(userId);
            }
            Session session = sessionRepository.getSession((user.getActiveSession()));
            if (session == null) {
                session = new Session(UUID.randomUUID().toString(), user.getUserID(), System.currentTimeMillis());
                sessionRepository.add(session);
                user.setActiveSession(session.getSessionId());
            }
            return session.getSessionId();
    }
}
