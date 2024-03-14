package com.mygdx.game;

import java.util.Map;
import java.util.Random;

public class MapGenerator {
    public static final int MIN_ROOM_WIDTH = 3;
    public static final int MAX_ROOM_WIDTH = 10;
    public static final int MIN_ROOM_HEIGHT = 3;
    public static final int MAX_ROOM_HEIGHT = 10;
    public static final int MAP_WIDTH = 35;
    public static final int MAP_HEIGHT = 35;
    public static final String TILE_TEXTURE = "assets/tiles/stonefloor1_0_0.png";

    public static int[][] generateMap() {
        Random random = new Random();
        int[][] map = new int[MAP_WIDTH][MAP_HEIGHT];

        // Generate rooms
        int numRooms = random.nextInt(10) + 5; // Generate between 5 to 15 rooms
        for (int i = 0; i < numRooms; i++) {
            int roomWidth = random.nextInt(MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1) + MIN_ROOM_WIDTH;
            int roomHeight = random.nextInt(MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1) + MIN_ROOM_HEIGHT;
            int roomX = random.nextInt(MAP_WIDTH - roomWidth);
            int roomY = random.nextInt(MAP_HEIGHT - roomHeight);
            for (int x = roomX; x < roomX + roomWidth; x++) {
                for (int y = roomY; y < roomY + roomHeight; y++) {
                    map[x][y] = 1; // Use 1 to represent floor tiles
                }
            }
        }

        // Print map (for demonstration purposes)
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (map[x][y] == 1) {
                    System.out.print("F "); // Floor tile
                } else {
                    System.out.print("W "); // Wall or empty tile
                }
            }
            System.out.println();
        }

        return map;
    }

    public static void main(String[] args) {
        generateMap();
    }
}