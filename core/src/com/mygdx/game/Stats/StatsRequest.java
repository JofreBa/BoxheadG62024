package com.mygdx.game.Stats;

import java.util.List;

public class StatsRequest {
        private String _id;
        private String id;
        private int score;
        private String time;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<String> getSkins() {
        return skins;
    }

    public void setSkins(List<String> skins) {
        this.skins = skins;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    private String character;
        private List<String> skins;
        private int games;

    public StatsRequest(String id, String id1, int score, String time, String character, List<String> skins, int games) {
        _id = id;
        this.id = id1;
        this.score = score;
        this.time = time;
        this.character = character;
        this.skins = skins;
        this.games = games;
    }
}



