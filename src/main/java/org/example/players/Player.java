package org.example.players;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Player implements PlayerInterface{
    private String name;
    private String nickname;
    private int number;
    private String teamName;

    public Player(String name, String nickname, int number, String teamName) {
        this.name = name;
        this.nickname = nickname;
        this.number = number;
        this.teamName = teamName;
    }

    @Override
    public int calculateRatingPoints() {
        return 0;
    }

    @Override
    public int getScoredPoints() {
        return 0;
    }
}
