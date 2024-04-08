package com.mygdx.game.Stats;

import java.lang.reflect.Array;

public class StatsRequest {
    private int Score, Coins;
    private String[] Skins;

    public StatsRequest(String[] Skins, int Score, int Coins) {
        this.Skins = Skins;
        this.Score = Score;
        this.Coins = Coins;
    }


}
