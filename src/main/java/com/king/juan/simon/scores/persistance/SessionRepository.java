package com.king.juan.simon.scores.persistance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.king.juan.simon.scores.model.Session;

public class SessionRepository {

    Map<String, Session> userSessions;

    public SessionRepository() {
        userSessions = new ConcurrentHashMap<>();
    }

    public Session add(Session session) {
        return userSessions.put(session.getSessionId(), session);
    }

    public Session getSession(String sessionKey) {
        Session session = sessionKey != null ? userSessions.get(sessionKey) : null;
        if (session != null) {
            if (session.isValid()) {
                return session;
            }
            endSession(sessionKey);
        }
        return null;
    }

    public void endSession(String sessionKey) {
        userSessions.remove(sessionKey);
    }
}
