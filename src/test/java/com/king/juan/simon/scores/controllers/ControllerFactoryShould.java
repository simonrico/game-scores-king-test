package com.king.juan.simon.scores.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.king.juan.simon.scores.server.CustomHttpExchange;

public class ControllerFactoryShould {



    @Mock
    private CustomHttpExchange customHttpExchange;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void returnRightControllerBaseOnRequestPath() {
        ControllerFactory controllerFactory = new ControllerFactory();

        Mockito.when(customHttpExchange.getRequestPath()).thenReturn("/1231/score");
        Assert.assertTrue(controllerFactory.getController(customHttpExchange) instanceof ScoreController);

        Mockito.when(customHttpExchange.getRequestPath()).thenReturn("/1231/login");
        Assert.assertTrue(controllerFactory.getController(customHttpExchange) instanceof LoginController);

        Mockito.when(customHttpExchange.getRequestPath()).thenReturn("/1231/highscorelist");
        Assert.assertTrue(controllerFactory.getController(customHttpExchange) instanceof HighScoreController);

    }

}