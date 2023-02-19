package org.example;

import org.example.config.MvpConfig;
import org.example.players.BasketballPlayer;
import org.example.players.Player;
import org.example.utils.GameReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.example.utils.GameAnalyzer;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameAnalyzerTest{

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    ApplicationContext context = new AnnotationConfigApplicationContext(MvpConfig.class);
    GameAnalyzer gameAnalyzer = context.getBean("gameAnalyzerBean", GameAnalyzer.class);
    GameReader gameReader = context.getBean("gameReaderBean", GameReader.class);
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void getPlayersFromAllMatchesTest() {
        List<String> data = new ArrayList<>();
        List<List<String>> matches = new ArrayList<>();
        data.add("BASKETBALL"); matches.add(data); data = new ArrayList<>();
        data.add("player 1");data.add("nick1");data.add("4");data.add("Team A");data.add("10");data.add("2");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 2");data.add("nick2");data.add("8");data.add("Team A");data.add("0");data.add("10");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 3");data.add("nick3");data.add("15");data.add("Team A");data.add("15");data.add("10");data.add("4"); matches.add(data); data = new ArrayList<>();
        data.add("player 4");data.add("nick4");data.add("16");data.add("Team B");data.add("20");data.add("0");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 5");data.add("nick5");data.add("23");data.add("Team B");data.add("4");data.add("7");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 6");data.add("nick6");data.add("42");data.add("Team B");data.add("8");data.add("10");data.add("0"); matches.add(data);

        List<List<Player>> playersResult = gameAnalyzer.getPlayersFromAllMatches(matches);

        List<List<Player>> playersAssert = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new BasketballPlayer("player 1", "nick1", 4, "Team A", 10, 2, 7));
        playerList.add(new BasketballPlayer("player 2", "nick2", 8, "Team A", 0, 10, 0));
        playerList.add(new BasketballPlayer("player 3", "nick3", 15, "Team A", 15, 10, 4));
        playerList.add(new BasketballPlayer("player 4", "nick4", 16, "Team B", 20, 0, 0));
        playerList.add(new BasketballPlayer("player 5", "nick5", 23, "Team B", 4, 7, 7));
        playerList.add(new BasketballPlayer("player 6", "nick6", 42, "Team B", 8, 10, 0));
        playersAssert.add(playerList);

        assertEquals(playersAssert, playersResult);
    }

    @Test
    public void getPlayersFromAllMatchesWithSameNicknameTest() {
        exit.expectSystemExitWithStatus(0);
        List<String> data = new ArrayList<>();
        List<List<String>> matches = new ArrayList<>();
        data.add("BASKETBALL"); matches.add(data); data = new ArrayList<>();
        data.add("player 1");data.add("nick1");data.add("4");data.add("Team A");data.add("10");data.add("2");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 2");data.add("nick2");data.add("8");data.add("Team A");data.add("0");data.add("10");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 3");data.add("nick3");data.add("15");data.add("Team A");data.add("15");data.add("10");data.add("4"); matches.add(data); data = new ArrayList<>();
        data.add("player 4");data.add("nick4");data.add("16");data.add("Team B");data.add("20");data.add("0");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 5");data.add("nick5");data.add("23");data.add("Team B");data.add("4");data.add("7");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 6");data.add("nick1");data.add("42");data.add("Team B");data.add("8");data.add("10");data.add("0"); matches.add(data);

        gameAnalyzer.getPlayersFromAllMatches(matches);
        assertEquals("Error! There are two players with the same nickname in one game: nick1", errContent.toString());

    }

    @Test
    public void getMvpTest() {
        List<List<String>> allGamesData = gameReader.readAllGames(System.getProperty("user.dir") + "/src/main/resources/test");
        List<List<Player>> allPlayers = gameAnalyzer.getPlayersFromAllMatches(allGamesData);

        gameAnalyzer.getResultOfMatches(allPlayers);
        gameAnalyzer.findTheMVP();
        String expectedString = "Successfully read the file basketball_game1.csv\r\n" +
                "Successfully read the file handball_game1.csv\r\n" +
                "Successfully read all the files \n" +
                "\r\n" +
                "==================================TOURNAMENT RATING TABLE==================================\r\n" +
                "        Name        |      Nickname      |   Number   |     Team name     |  Rating points  \r\n" +
                "===========================================================================================\r\n" +
                " player 1            | nick1               | 4           | Team A             | 19                \r\n" +
                " player 6            | nick6               | 42          | Team B             | 27                \r\n" +
                " player 5            | nick5               | 23          | Team B             | 31                \r\n" +
                " player 2            | nick2               | 8           | Team A             | 30                \r\n" +
                " player 4            | nick4               | 16          | Team B             | 25                \r\n" +
                " player 3            | nick3               | 15          | Team A             | 54                \r\n" +
                "\r\n" +
                "The MVP of tournament is player 3 with nickname \"nick3\". He/She got 54 rating points!\r\n";
        assertEquals(expectedString, outContent.toString());

    }

    @After
    public void restoreStream() {
        System.setErr(originalErr);
        System.setOut(originalOut);
    }
}
