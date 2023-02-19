package org.example;

import org.example.config.MvpConfig;
import org.example.players.Player;
import org.example.utils.GameAnalyzer;
import org.example.utils.GameReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main
{

    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(MvpConfig.class);
        GameReader gameReader = context.getBean("gameReaderBean", GameReader.class);
        GameAnalyzer gameAnalyzer = context.getBean("gameAnalyzerBean", GameAnalyzer.class);

        List<List<String>> allGamesData = gameReader.readAllGames();
        List<List<Player>> allPlayers = gameAnalyzer.getPlayersFromAllMatches(allGamesData);

        gameAnalyzer.getResultOfMatches(allPlayers);
        gameAnalyzer.findTheMVP();
    }
}
