package org.example;

import org.example.config.MvpConfig;
import org.example.players.BasketballPlayer;
import org.example.players.Player;
import org.example.utils.GameReader;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameReaderTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(MvpConfig.class);
    GameReader gameReader = context.getBean("gameReaderBean", GameReader.class);

    @Test
    public void readAllGamesTest() {
        List<String> data = new ArrayList<>();
        List<List<String>> matches = new ArrayList<>();
        data.add("BASKETBALL"); matches.add(data); data = new ArrayList<>();
        data.add("player 1");data.add("nick1");data.add("4");data.add("Team A");data.add("10");data.add("2");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 2");data.add("nick2");data.add("8");data.add("Team A");data.add("0");data.add("10");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 3");data.add("nick3");data.add("15");data.add("Team A");data.add("15");data.add("10");data.add("4"); matches.add(data); data = new ArrayList<>();
        data.add("player 4");data.add("nick4");data.add("16");data.add("Team B");data.add("20");data.add("0");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("player 5");data.add("nick5");data.add("23");data.add("Team B");data.add("4");data.add("7");data.add("7"); matches.add(data); data = new ArrayList<>();
        data.add("player 6");data.add("nick6");data.add("42");data.add("Team B");data.add("8");data.add("10");data.add("0"); matches.add(data); data = new ArrayList<>();
        data.add("HANDBALL"); matches.add(data); data = new ArrayList<>();
        data.add("player 1");data.add("nick1");data.add("4");data.add("Team A");data.add("0");data.add("20"); matches.add(data);  data = new ArrayList<>();
        data.add("player 2");data.add("nick2");data.add("8");data.add("Team A");data.add("15");data.add("20"); matches.add(data);  data = new ArrayList<>();
        data.add("player 3");data.add("nick3");data.add("15");data.add("Team A");data.add("10");data.add("20"); matches.add(data);  data = new ArrayList<>();
        data.add("player 4");data.add("nick4");data.add("16");data.add("Team B");data.add("0");data.add("25"); matches.add(data); data = new ArrayList<>();
        data.add("player 5");data.add("nick5");data.add("23");data.add("Team B");data.add("12");data.add("25"); matches.add(data);  data = new ArrayList<>();
        data.add("player 6");data.add("nick6");data.add("42");data.add("Team B");data.add("8");data.add("25"); matches.add(data);

        List<List<String>> allData = gameReader.readAllGames(System.getProperty("user.dir") + "/src/main/resources/test");

        assertEquals(matches, allData);
    }
}
