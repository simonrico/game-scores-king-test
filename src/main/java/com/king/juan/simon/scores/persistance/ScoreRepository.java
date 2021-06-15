package com.king.juan.simon.scores.persistance;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.king.juan.simon.scores.model.Score;

public class ScoreRepository {
    Map<Integer, Map<Integer, Score>> scoresPerLevel;

    public ScoreRepository() {
        scoresPerLevel = new ConcurrentHashMap<>();
    }

    public void addNewScore(Integer level, Score score) {
        Map<Integer, Score> scores = scoresPerLevel.get(level);
        if (scores == null) {
            scores = new HashMap<>();
            scores.put(score.getUser().getUserID(), score);
            scoresPerLevel.put(level, scores);
        } else {
            scores.put(score.getUser().getUserID(), score);
        }
    }

    public Score getScoreByLevelAndUser(Integer level, Integer userId) {
        return Optional.ofNullable(scoresPerLevel.get(level)).map(scores -> scores.get(userId)).orElse(null);
    }

    public Collection<Score> getScoresByLevel(Integer level) {
        return Optional.ofNullable(scoresPerLevel.get(level)).orElse(new HashMap<>()).values();
    }

}
