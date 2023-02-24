package org.example.utils;

import org.example.players.Player;
import org.example.players.factory.KindsOfSports;
import org.example.players.factory.PlayerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class GameReader {
    //getting all players from files
    public List<List<Player>> getPlayersFromAllMatches(String pathname){
        return findPlayersFromAllMatches(readAllGames(pathname));
    }

    //reading all csv files and converting them into list of files (lists of strings)
    private List<List<String>> readAllGames(String pathname){
        File[] files = new File(pathname).listFiles(x -> x.isFile() && x.getName().endsWith(".csv"));
        List<List<String>> records = new ArrayList<>();

        if (files != null) {
            for (File file: files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(";");
                        records.add(Arrays.asList(values));
                    }
                    System.out.println("Successfully read the file " + file.getName());

                } catch (IOException e) {
                    System.err.println("An IOException caught in GameReader.readAllGames(): something wrong happened while reading the file " + file.getName());
                    System.exit(0);
                }

            }
        }
        System.out.println("Successfully read all the files \n");

        return records;
    }

    //converting data from csv into player objects
    private List<List<Player>> findPlayersFromAllMatches(List<List<String>> allGamesData){

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
            if(!playersFromOneGame.isEmpty() && game.size() == 1 || !playersFromOneGame.isEmpty() && allGamesData.get(allGamesData.size() - 1).equals(game)){
                isPlayerNicknameUnique(playersFromOneGame);
                allPlayers.add(playersFromOneGame);
                playersFromOneGame = new ArrayList<>();
            }
        }
        return allPlayers;
    }

    //checking if all nicknames unique in the match
    private void isPlayerNicknameUnique(List<Player> playersFromOneGame){
        for (Player firstPlayer: playersFromOneGame) {
            for (Player secondPlayer: playersFromOneGame) {
                if(firstPlayer.getNickname().equals(secondPlayer.getNickname()) && firstPlayer != secondPlayer){
                    System.err.println("Error! There are two players with the same nickname in one game: " + firstPlayer.getNickname());
                    System.exit(0);
                }
            }
        }
    }
}