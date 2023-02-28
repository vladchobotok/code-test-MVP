package org.example;

import org.example.players.Player;
import org.example.utils.GameReader;
import org.junit.Before;
import org.junit.Test;
import org.example.utils.GameAnalyzer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameAnalyzerTest{

    private final GameAnalyzer gameAnalyzer;
    private final GameReader gameReader;

    @Autowired
    GameAnalyzerTest(GameAnalyzer gameAnalyzer, GameReader gameReader){
        this.gameAnalyzer = gameAnalyzer;
        this.gameReader = gameReader;
    }

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
