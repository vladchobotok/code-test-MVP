package org.example.utils;


import org.example.players.Player;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResultPrinter {
    //method to print the results
    public void printTheResults(Map.Entry<Player, Integer> mvp, Map<Player, Integer> playerRatingTable){
        System.out.println("==================================TOURNAMENT RATING TABLE==================================");
        System.out.println("        Name        |      Nickname      |   Number   |     Team name     |  Rating points  ");
        System.out.println("===========================================================================================");

        for(Map.Entry<Player, Integer> playersRating : playerRatingTable.entrySet()) {
            System.out.printf(" %-20s| %-20s| %-12d| %-19s| %-17d %n",playersRating.getKey().getName(),playersRating.getKey().getNickname(),
                    playersRating.getKey().getNumber(),playersRating.getKey().getTeamName(),playersRating.getValue());

        }
        System.out.println();
        System.out.println("The MVP of tournament is " + mvp.getKey().getName() + " with nickname " + "\"" + mvp.getKey().getNickname() + "\". " +
                "He/She got " + mvp.getValue() + " rating points!");
    }
}
