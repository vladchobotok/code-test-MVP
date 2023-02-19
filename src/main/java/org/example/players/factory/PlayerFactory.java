package org.example.players.factory;

import org.example.players.BasketballPlayer;
import org.example.players.HandballPlayer;
import org.example.players.Player;

public class PlayerFactory {

    public static Player createPlayer(KindsOfSports kindsOfSports, String[] playerInfo) {
        switch (kindsOfSports){
            case BASKETBALL:
                return new BasketballPlayer(playerInfo[0], playerInfo[1], Integer.parseInt(playerInfo[2]), playerInfo[3], Integer.parseInt(playerInfo[4]), Integer.parseInt(playerInfo[5]), Integer.parseInt(playerInfo[6]));
            case HANDBALL:
                return new HandballPlayer(playerInfo[0], playerInfo[1], Integer.parseInt(playerInfo[2]), playerInfo[3], Integer.parseInt(playerInfo[4]), Integer.parseInt(playerInfo[5]));
        }
        return null;
    }
}
