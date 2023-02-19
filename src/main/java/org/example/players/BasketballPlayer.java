package org.example.players;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BasketballPlayer extends Player implements PlayerInterface{

    public static final int SCORED_POINTS_COEFFICIENT = 2;
    public static final int REBOUNDS_COEFFICIENT = 1;
    public static final int ASSISTS_COEFFICIENT = 1;
    private int scoredPoints;
    private int rebounds;
    private int assists;

    public BasketballPlayer(String name, String nickname, int number, String teamName, int scoredPoints, int rebounds, int assists){
        super(name, nickname, number, teamName);
        this.scoredPoints = scoredPoints;
        this.rebounds = rebounds;
        this.assists = assists;
    }

    @Override
    public int calculateRatingPoints() {
        return scoredPoints * SCORED_POINTS_COEFFICIENT +
                rebounds * REBOUNDS_COEFFICIENT +
                assists * ASSISTS_COEFFICIENT;
    }

    @Override
    public int getScoredPoints() {
        return scoredPoints;
    }
}
