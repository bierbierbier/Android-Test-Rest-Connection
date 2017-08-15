package com.example.nadine.hello;

/**
 * Created by Nadine on 14.08.2017.
 */

public class Map {
    private int map_id;
    private String map_name;

    public Map(int map_id, String map_name) {
        this.map_id = map_id;
        this.map_name = map_name;
    }

    public int getMapId() {
        return map_id;
    }

    public String getMapName() {
        return map_name;
    }

    @Override
    public String toString() {
        return "Id: " + this.map_id + ", Name: " + this.map_name;
    }
}
