package com.king.juan.simon.scores.controllers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

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
import com.king.juan.simon.scores.model.Session;
import com.king.juan.simon.scores.model.User;
import com.king.juan.simon.scores.persistance.ScoreRepository;
import com.king.juan.simon.scores.persistance.SessionRepository;
import com.king.juan.simon.scores.persistance.UserRepository;
import com.king.juan.simon.scores.server.CustomHttpExchange;

public class ScoreControllerShould {

    private ScoreRepository scoreRepository;

    @Mock
    private CustomHttpExchange customHttpExchange;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveHighestScorePerUserPerLevel() throws IOException {
        scoreRepository = new ScoreRepository();
        ScoreController scoreController = new ScoreController(scoreRepository, sessionRepository, userRepository);
        User user = new User(1);
        Mockito.when(sessionRepository.getSession(any())).thenReturn(new Session("xxx", 1, System.currentTimeMillis()));
        Mockito.when(customHttpExchange.getRequestMethod()).thenReturn("POST");
        Mockito.when(customHttpExchange.getPathVariable(any(), anyInt())).thenReturn(1);
        Mockito.when(customHttpExchange.getRequestBodyAsInt()).thenReturn(10);
        Mockito.when(customHttpExchange.getParameter(any())).thenReturn("xxx");
        Mockito.when(userRepository.getUser(any())).thenReturn(user);
        scoreController.execute(customHttpExchange);
        Assert.assertEquals(10,scoreRepository.getScoreByLevelAndUser(1,1).getScore());

        Mockito.when(customHttpExchange.getRequestBodyAsInt()).thenReturn(20);
        Assert.assertEquals("", scoreController.execute(customHttpExchange));
        Assert.assertEquals(20,scoreRepository.getScoreByLevelAndUser(1,1).getScore());


        Mockito.when(customHttpExchange.getRequestBodyAsInt()).thenReturn(5);
        Assert.assertEquals("", scoreController.execute(customHttpExchange));
        Assert.assertEquals(20,scoreRepository.getScoreByLevelAndUser(1,1).getScore());
    }

}