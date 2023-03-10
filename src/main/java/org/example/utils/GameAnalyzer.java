package org.example.utils;

import lombok.Getter;
import org.example.players.Player;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Getter
public class GameAnalyzer {
    private final Map<Player, Integer> playerRatingTable = new HashMap<>();

    //iterating through the games and calculating players' rating points, returning an MVP of the tournament
    public Map.Entry<Player, Integer> getMvpOfTournament(List<List<Player>> allPlayers) throws NoSuchElementException{

        allPlayers.forEach(this::calculateWinnerTeamAndUpdateRating);

        return findTheMVP();
    }
    // calculating winner of the game and updating tournament rating table
    private void calculateWinnerTeamAndUpdateRating(List<Player> playerList){
        Map<Player, Integer> playerRating = new IdentityHashMap<>();
        Map<String, Integer> teamsScoreboard = new HashMap<>();

        playerList.forEach(player -> {
            playerRating.put(player, player.calculateRatingPoints());
            if (!teamsScoreboard.containsKey(player.getTeamName())) {
                teamsScoreboard.put(player.getTeamName(), 0);
            }
            teamsScoreboard.put(player.getTeamName(), teamsScoreboard.get(player.getTeamName()) + player.getScoredPoints());
        });

        String winner = teamsScoreboard.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        playerRating.entrySet()
                .stream()
                .filter(playerEntry -> playerEntry.getKey().getTeamName().equals(winner))
                .forEach(playerEntry -> playerRating.put(playerEntry.getKey(), playerEntry.getValue() + 10));

        updatePlayerRatingTable(playerRating);
    }

    //filling player rating table with actual players' rating points
    private void updatePlayerRatingTable(Map<Player,Integer> playerRating) {
        playerRating.forEach((player, playerScore) -> {
            player = new Player(player.getName(), player.getNickname(), player.getNumber(), player.getTeamName());
            if (playerRatingTable.containsKey(player)) {
                playerRatingTable.put(player, playerRatingTable.get(player) + playerScore);
            }
            else{
                if(isNicknameUnique(player, playerScore)){
                    playerRatingTable.put(player, playerScore);
                }
            }
        });
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

        final Map.Entry<Player, Integer>[] mvp = new Map.Entry[]{Map.entry(new Player(), -1)};

        playerRatingTable.forEach((k, v) -> {
            if(v > mvp[0].getValue()){
                mvp[0] = Map.entry(k, v);
            }
        });

        return mvp[0];
    }
}