package org.example;

import org.example.players.Player;
import org.example.config.AppConfig;
import org.example.utils.GameReader;
import org.junit.Before;
import org.junit.Test;
import org.example.utils.GameAnalyzer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GameAnalyzerTest{
    @Autowired
    private GameAnalyzer gameAnalyzer;
    @Autowired
    private GameReader gameReader;
    private List<List<Player>> allPlayers;
    private int expectedMvpRatingScore;

    @Before
    public void setUpVariables() {
        allPlayers = gameReader.getPlayersFromAllMatches(System.getProperty("user.dir") + "/src/main/resources/test1");
        expectedMvpRatingScore = 54;
    }

    @Test
    public void getMvpTest() {
        int actualMvpRatingScore = gameAnalyzer.getMvpOfTournament(allPlayers).getValue();
        assertEquals(expectedMvpRatingScore, actualMvpRatingScore);
    }
}
