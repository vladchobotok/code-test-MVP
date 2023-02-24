package org.example.utils;

import lombok.Getter;
import org.example.players.Player;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Getter
public class GameAnalyzer {
    private final Map<Player, Integer> playerRatingTable = new HashMap<>();

    //iterating through the games and calculating players' rating points
    public Map.Entry<Player, Integer> getMvpOfTournament(List<List<Player>> allPlayers){
        for (List<Player> playerList: allPlayers) {
            Map<Player, Integer> playerRating = new IdentityHashMap<>();
            Map<String, Integer> teamsScoreboard = new HashMap<>();
            for (Player player : playerList) {
                playerRating.put(player, player.calculateRatingPoints());
                if (!teamsScoreboard.containsKey(player.getTeamName())) {
                    teamsScoreboard.put(player.getTeamName(), 0);
                }
            }

            calculateMatchWinner(playerList, playerRating, teamsScoreboard);
            updatePlayerRatingTable(playerRating);
        }

        return findTheMVP();
    }
    //calculating match winner and giving 10 additional rating points to winners
    private void calculateMatchWinner(List<Player> playerList, Map<Player, Integer> playerRating, Map<String, Integer> teamScoreboard){
        for (Player player: playerList) {
            for (Map.Entry<String, Integer> team : teamScoreboard.entrySet()) {
                if (team.getKey().equals(player.getTeamName())) {
                    teamScoreboard.put(team.getKey(), team.getValue() + player.getScoredPoints());
                }
            }
        }

        List<String> teamList = new ArrayList<>(teamScoreboard.keySet());
        String winner = teamList.get(0);

        if(teamScoreboard.get(teamList.get(0)) < teamScoreboard.get(teamList.get(1))){
            winner = teamList.get(1);
        }

        for (Map.Entry<Player, Integer> player: playerRating.entrySet()) {
            if(player.getKey().getTeamName().equals(winner)){
                playerRating.put(player.getKey(), player.getValue() + 10);
            }
        }
    }
    //filling player rating table with actual players' rating points
    private void updatePlayerRatingTable(Map<Player,Integer> playerRating) {

        for(Map.Entry<Player, Integer> player : playerRating.entrySet()) {
            player = new AbstractMap.SimpleEntry<>(new Player(player.getKey().getName(), player.getKey().getNickname(), player.getKey().getNumber(), player.getKey().getTeamName()), player.getValue());
            if (playerRatingTable.containsKey(player.getKey())) {
                    playerRatingTable.put(player.getKey(), playerRatingTable.get(player.getKey()) + player.getValue());
            }
            else{
                if(isNicknameUnique(player.getKey(), player.getValue())){
                    playerRatingTable.put(player.getKey(), player.getValue());
                }
            }
        }
    }
    //additional method to check nickname uniqueness in player rating table
    private boolean isNicknameUnique(Player player, int ratingScore){
        Player playerToDelete = null;
        Map<Player, Integer> playerToAdd = null;
        for(Map.Entry<Player, Integer> playersRating : playerRatingTable.entrySet()) {
            if(!playersRating.getKey().equals(player) && playersRating.getKey().getNickname().equals(player.getNickname())) {
                if (playersRating.getKey().getName().equals(player.getName())) {
                    playerToAdd = Map.of(player,playersRating.getValue() + ratingScore);
                    playerToDelete = playersRating.getKey();
                }
                if(playerToDelete != null) {
                    playerRatingTable.remove(playerToDelete);
                    playerRatingTable.put(playerToAdd.entrySet().iterator().next().getKey(), playerToAdd.entrySet().iterator().next().getValue());
                    return false;
                }
            }
        }
        return true;
    }
    //method to find mvp
    private Map.Entry<Player, Integer> findTheMVP(){

        Map.Entry<Player, Integer> mvp = Map.entry(new Player(), -1);

        for (Map.Entry<Player, Integer> player: playerRatingTable.entrySet()) {
            if(player.getValue() > mvp.getValue()){
                mvp = player;
            }
        }

        return mvp;
    }
}