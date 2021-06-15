package com.king.juan.simon.scores;

import java.io.IOException;

import com.king.juan.simon.scores.server.ScoreServer;

public class Main {

    public static void main(String[] args) {
        int port = 8080;

        try {
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            new ScoreServer().start(port);
            System.out.println(String.format("Server started. Listening on http://localhost:%d", port));
        } catch (IOException e) {
            System.out.println(String.format("Server could not be started. %s", e.getMessage()));
        }
    }
}
