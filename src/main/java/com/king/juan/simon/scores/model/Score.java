package com.king.juan.simon.scores.model;

public class Score implements Comparable<Score> {
    private User user;
    private int score;

    public Score (User user, int score) {
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score o) {
        return Integer.compare(score, o.getScore());
    }
}
