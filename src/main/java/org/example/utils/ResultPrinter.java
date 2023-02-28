package org.example.utils;

import org.example.players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ResultPrinter implements CommandLineRunner {

    private final GameReader gameReader;
    private final GameAnalyzer gameAnalyzer;

    @Autowired
    public ResultPrinter(GameReader gameReader, GameAnalyzer gameAnalyzer) {
        this.gameReader = gameReader;
        this.gameAnalyzer = gameAnalyzer;
    }

    //method to calculate the mvp
    @Override
    public void run(String... args) {
        List<List<Player>> allPlayers = gameReader.getPlayersFromAllMatches(System.getProperty("user.dir") + "/src/main/resources");
        Map.Entry<Player, Integer> mvp = gameAnalyzer.getMvpOfTournament(allPlayers);
        Map<Player, Integer> playerRatingTable = gameAnalyzer.getPlayerRatingTable();
        printTheResults(mvp, playerRatingTable);
    }

    //method to print the results
    public void printTheResults(Map.Entry<Player, Integer> mvp, Map<Player, Integer> playerRatingTable){
        System.out.println("==================================TOURNAMENT RATING TABLE==================================");
        System.out.println("        Name        |      Nickname      |   Number   |     Team name     |  Rating points  ");
        System.out.println("===========================================================================================");

        playerRatingTable.forEach((k, v) -> System.out.printf(" %-20s| %-20s| %-12d| %-19s| %-17d %n", k.getName(), k.getNickname(), k.getNumber(), k.getTeamName(), v));

        System.out.println();
        System.out.println("The MVP of tournament is " + mvp.getKey().getName() + " with nickname " + "\"" + mvp.getKey().getNickname() + "\". " +
                "He/She got " + mvp.getValue() + " rating points!\n");
    }
}
