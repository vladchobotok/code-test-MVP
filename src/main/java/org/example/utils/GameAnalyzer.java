package org.example.utils;

import org.example.players.Player;
import org.example.players.factory.KindsOfSports;
import org.example.players.factory.PlayerFactory;

import java.util.*;
public class GameAnalyzer {
    private Map<Player, Integer> playerRatingTable = new HashMap<>();

    //converting data from csv into players
    public List<List<Player>> getPlayersFromAllMatches(List<List<String>> allGamesData){
        KindsOfSports kindOfSports = null;
        List<List<Player>> allPlayers = new ArrayList<>();
        List<Player> playersFromOneGame = new ArrayList<>();

        for (List<String> game: allGamesData) {
            if(game.size() == 1){
                kindOfSports = KindsOfSports.valueOf(game.get(0));
            }
            else{
                Player player = PlayerFactory.createPlayer(kindOfSports, game.toArray(new String[0]));
                playersFromOneGame.add(player);

            }
            for (Player firstPlayer: playersFromOneGame) {
                for (Player secondPlayer: playersFromOneGame) {
                    if(firstPlayer.getNickname().equals(secondPlayer.getNickname()) && firstPlayer != secondPlayer){
                        System.err.println("Error! There are two players with the same nickname in one game: " + firstPlayer.getNickname());
                        System.exit(0);
                    }
                }
            }
            if(!playersFromOneGame.isEmpty() && game.size() == 1 || !playersFromOneGame.isEmpty() && allGamesData.get(allGamesData.size() - 1).equals(game)){
                allPlayers.add(playersFromOneGame);
                playersFromOneGame = new ArrayList<>();
            }
        }
        return allPlayers;
    }

    //iterating through the games and calculating players' rating points
    public void getResultOfMatches(List<List<Player>> allPlayers){

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
    }
    //calculating match winner and giving 10 additional rating points to winners
    private void calculateMatchWinner(List<Player> playerList, Map<Player, Integer> playerRating, Map<String, Integer> teamScoreboard){
        for (Player player: playerList) {
            for (Map.Entry<String, Integer> team: teamScoreboard.entrySet()) {
                if(team.getKey().equals(player.getTeamName())){
                    teamScoreboard.put(team.getKey(), team.getValue() + player.getScoredPoints());
                }
            }
        }

        String winner = "";
        List<String> teamList = new ArrayList<>(teamScoreboard.keySet());

        if(teamScoreboard.get(teamList.get(0)) > teamScoreboard.get(teamList.get(1))){
            winner = teamList.get(0);
        }
        else{
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
                    playerRatingTable.put(playerToAdd.entrySet().iterator().next().getKey(),
                            playerToAdd.entrySet().iterator().next().getValue());
                    return false;
                }
            }
        }

        return true;
    }
    //method to find mvp and print the results
    public void findTheMVP(){

        Map.Entry<Player, Integer> mvp = Map.entry(new Player(), -1);

        for (Map.Entry<Player, Integer> player: playerRatingTable.entrySet()) {
            if(player.getValue() > mvp.getValue()){
                mvp = player;
            }
        }
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
