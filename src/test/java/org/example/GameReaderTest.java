package org.example;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.example.players.BasketballPlayer;
import org.example.players.Player;
import org.example.config.AppConfig;
import org.example.utils.GameReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GameReaderTest {
    @Autowired
    private GameReader gameReader;
    private List<List<Player>> expectedPlayers;

    @Before
    public void setUpVariables() {
        expectedPlayers = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new BasketballPlayer("player 1", "nick1", 4, "Team A", 10, 2, 7));
        playerList.add(new BasketballPlayer("player 2", "nick2", 8, "Team A", 0, 10, 0));
        playerList.add(new BasketballPlayer("player 3", "nick3", 15, "Team A", 15, 10, 4));
        playerList.add(new BasketballPlayer("player 4", "nick4", 16, "Team B", 20, 0, 0));
        playerList.add(new BasketballPlayer("player 5", "nick5", 23, "Team B", 4, 7, 7));
        playerList.add(new BasketballPlayer("player 6", "nick6", 42, "Team B", 8, 10, 0));
        expectedPlayers.add(playerList);
    }

    @Test
    public void getPlayersFromAllMatchesWithRepeatingNicknameTest() throws Exception {
        int actualStatus = SystemLambda.catchSystemExit(() -> gameReader.getPlayersFromAllMatches(System.getProperty("user.dir") + "/src/main/resources/test2"));
        assertEquals(0, actualStatus);
    }

    @Test
    public void getPlayersFromAllMatchesTest() {
        List<List<Player>> actualPlayers = gameReader.getPlayersFromAllMatches(System.getProperty("user.dir") + "/src/main/resources/test3");
        assertEquals(expectedPlayers, actualPlayers);
    }
}
