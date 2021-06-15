package com.king.juan.simon.scores.persistance;

public class RepositoryFactory {

    private ScoreRepository scoreRepository;

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    public ScoreRepository getScoreRepository() {
        if(scoreRepository == null) {
            scoreRepository = new ScoreRepository();
        }
        return scoreRepository;
    }

    public UserRepository getUserRepository() {
        if(userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public SessionRepository getSessionRepository() {
        if(sessionRepository == null) {
            sessionRepository = new SessionRepository();
        }
        return sessionRepository;
    }
}
