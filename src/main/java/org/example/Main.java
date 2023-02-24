package org.example;

import org.example.players.Player;
import org.example.config.AppConfig;
import org.example.utils.GameAnalyzer;
import org.example.utils.GameReader;
import org.example.utils.ResultPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Main {
    private static GameReader gameReader;
    private static GameAnalyzer gameAnalyzer;
    private static ResultPrinter resultPrinter;

    @Autowired
    public Main(GameReader gameReader, GameAnalyzer gameAnalyzer, ResultPrinter resultPrinter) {
        Main.gameReader = gameReader;
        Main.gameAnalyzer = gameAnalyzer;
        Main.resultPrinter = resultPrinter;
    }

    public static void main(String[] args)
    {
        new AnnotationConfigApplicationContext(AppConfig.class);

        List<List<Player>> allPlayers = gameReader.getPlayersFromAllMatches(System.getProperty("user.dir") + "/src/main/resources");
        Map.Entry<Player, Integer> mvp = gameAnalyzer.getMvpOfTournament(allPlayers);
        Map<Player, Integer> playerRatingTable = gameAnalyzer.getPlayerRatingTable();
        resultPrinter.printTheResults(mvp, playerRatingTable);
    }
}
