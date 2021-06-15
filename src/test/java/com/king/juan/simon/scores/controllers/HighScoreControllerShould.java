package com.king.juan.simon.scores.controllers;

import static org.mockito.Matchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.king.juan.simon.scores.model.Score;
import com.king.juan.simon.scores.model.User;
import com.king.juan.simon.scores.persistance.ScoreRepository;
import com.king.juan.simon.scores.server.CustomHttpExchange;

public class HighScoreControllerShould {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private CustomHttpExchange customHttpExchange;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnFifteenHighestScores() throws IOException {
        HighScoreController highScoreController = new HighScoreController(scoreRepository);
        Collection<Score>  scores= new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            scores.add(new Score(new User(i), i));
        }
        Mockito.when(scoreRepository.getScoresByLevel(any())).thenReturn(scores);
        Mockito.when(customHttpExchange.getRequestMethod()).thenReturn("GET");
        Assert.assertEquals("20=20,19=19,18=18,17=17,16=16,15=15,14=14,13=13,12=12,11=11,10=10,9=9,8=8,7=7,6=6",
                highScoreController.execute(customHttpExchange));
    }

}