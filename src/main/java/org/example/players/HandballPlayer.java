package org.example.players;

import lombok.Data;
@Data
public class HandballPlayer extends Player implements PlayerInterface{

    public static final int GOALS_MADE_COEFFICIENT = 2;
    public static final int GOALS_RECEIVED_COEFFICIENT = -1;
    private int goalsMade;
    private int goalsReceived;

    public HandballPlayer(String name, String nickname, int number, String teamName, int goalsMade, int goalsReceived){
        super(name, nickname, number, teamName);
        this.goalsMade = goalsMade;
        this.goalsReceived = goalsReceived;
    }

    @Override
    public int calculateRatingPoints() {
        return goalsMade * GOALS_MADE_COEFFICIENT +
                goalsReceived * GOALS_RECEIVED_COEFFICIENT;
    }

    @Override
    public int getScoredPoints() {
        return goalsMade;
    }
}
