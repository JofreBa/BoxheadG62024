package com.mygdx.game.MapsGenerator;

import com.badlogic.gdx.math.Interpolation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;

public class MapGenerator {

    // Constants
    private static final int MIN_ROOMS = 5;
    private static final int MAX_ROOMS = 10;
    private static final int MIN_ROOM_WIDTH = 9;
    private static final int MAX_ROOM_WIDTH = 20;
    private static final int MIN_ROOM_HEIGHT = 5;
    private static final int MAX_ROOM_HEIGHT = 10;
    private static final int CORRIDOR_WIDTH = 3;
    private static final int MIN_DISTANCE_BETWEEN_ROOMS = 9;
    private static final int MAX_DISTANCE_BETWEEN_ROOMS = 24;

    public static TileData[][] generateMap() {
        List<Room> rooms = new ArrayList<>();
        List<Corridor> corridors = new ArrayList<>();
        Random random = new Random();

        // Generate number of rooms
        int numRooms = random.nextInt(MAX_ROOMS - MIN_ROOMS + 1) + MIN_ROOMS;

        // Generate first room
        int startX = 1;
        int startY = 1;
        int width = random.nextInt(MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1) + MIN_ROOM_WIDTH;
        int height = random.nextInt(MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1) + MIN_ROOM_HEIGHT;
        rooms.add(new Room(startX, startY, width, height));

        // Generate subsequent rooms
        for (int i = 1; i < numRooms; i++) {
            Room prevRoom = rooms.get(i - 1);
            Room room = new Room(0, 0, 0, 0);
            Corridor corridor = new Corridor(0, 0, 0, 0);
            boolean collides = true;
            while (collides) {
                int newX, newY, corridorX, corridorY, corridorW, corridorH;
                collides = false;

                // Randomly choose one of the three cases: right, top, or bottom
                int caseSelector = random.nextInt(3);

                switch (caseSelector) {
                    case 0: // Generate room to the right
                        newX = prevRoom.getX() + prevRoom.getWidth()
                                + random.nextInt(MAX_DISTANCE_BETWEEN_ROOMS - MIN_DISTANCE_BETWEEN_ROOMS + 1)
                                + MIN_DISTANCE_BETWEEN_ROOMS;
                        newY = prevRoom.getY();

                        break;
                    case 1: // Generate room at the top
                        newX = prevRoom.getX();
                        newY = prevRoom.getY() - prevRoom.getHeight()
                                - random.nextInt(MAX_DISTANCE_BETWEEN_ROOMS - MIN_DISTANCE_BETWEEN_ROOMS + 1)
                                - MIN_DISTANCE_BETWEEN_ROOMS;
                        break;
                    case 2: // Generate room at the bottom
                        newX = prevRoom.getX();
                        newY = prevRoom.getY() + prevRoom.getHeight()
                                + random.nextInt(MAX_DISTANCE_BETWEEN_ROOMS - MIN_DISTANCE_BETWEEN_ROOMS + 1)
                                + MIN_DISTANCE_BETWEEN_ROOMS;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + caseSelector);

                }

                width = random.nextInt(MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1) + MIN_ROOM_WIDTH;
                height = random.nextInt(MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1) + MIN_ROOM_HEIGHT;
                room = new Room(newX, newY, width, height);
                collides = roomCollision(room, rooms);
                corridorX = prevRoom.getX() + random.nextInt(prevRoom.getWidth() / 2) + 1;
                corridorY = prevRoom.getY() + random.nextInt(prevRoom.getHeight() / 2) + 1;
                // corridors
                switch (caseSelector) {
                    case 0: // Generate room to the right
                        corridorW = newX - corridorX;
                        corridorH = 3;

                        break;
                    case 1: // Generate room at the top
                        corridorX = room.getX() + random.nextInt(room.getWidth() / 2) + 1;
                        corridorY = room.getY() + random.nextInt(room.getHeight() / 2) + 1;
                        corridorW = 3;
                        corridorH = Math.abs(corridorY - prevRoom.getY());
                        System.out.println(corridorH);
                        break;
                    case 2: // Generate room at the bottom
                        corridorW = 3;
                        corridorH = newY - corridorY;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + caseSelector);

                }
                corridor = new Corridor(corridorX, corridorY, corridorW, corridorH);
            }

            rooms.add(room);
            corridors.add(corridor);
        }

        printMap(rooms, corridors);
        return getMapTiles(rooms, corridors);
    }

    public static boolean roomCollision(Room newRoom, List<Room> existingRooms) {
        // Expand the new room's rectangle by 4 units in all directions
        Rectangle newRect = new Rectangle(newRoom.getX() - 4, newRoom.getY() - 4,
                newRoom.getWidth() + 8, newRoom.getHeight() + 8);

        // Iterate through existing rooms to check for overlap and minimum distance
        for (Room existingRoom : existingRooms) {
            // Convert existingRoom to a Rectangle
            Rectangle existingRect = new Rectangle(existingRoom.getX() - 4, existingRoom.getY() - 4,
                    existingRoom.getWidth() + 8, existingRoom.getHeight() + 8);

            // Check for overlap
            if (newRect.intersects(existingRect) || existingRect.intersects(newRect)) {

                return true; // Overlapping rooms
            }

        }
        return false;
    }

    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.generateMap();
    }

    public static void printMap(List<Room> rooms, List<Corridor> corridors) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Find bounds of the map
        for (Room room : rooms) {
            minX = Math.min(minX, room.getX()) -5;
            minY = Math.min(minY, room.getY()) -5;
            maxX = Math.max(maxX, room.getX() + room.getWidth());
            maxY = Math.max(maxY, room.getY() + room.getHeight());
        }

        char[][] map = new char[maxY - minY + 5][maxX - minX + 5]; // Adjust dimensions according to bounds
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = '.';
            }
        }

        for (Room room : rooms) {
            for (int i = room.getX(); i < room.getX() + room.getWidth(); i++) {
                for (int j = room.getY(); j < room.getY() + room.getHeight(); j++) {
                    map[j - minY][i - minX] = 'F';
                }
            }
        }

        for (Corridor corridor : corridors) {
            for (int i = corridor.getX(); i < corridor.getX() + corridor.getWidth(); i++) {
                for (int j = corridor.getY(); j < corridor.getY() + corridor.getHeight(); j++) {
                    map[j - minY][i - minX] = 'F';
                }
            }
        }
        

    }

    public static TileData[][] getMapTiles(List<Room> rooms, List<Corridor> corridors) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Find bounds of the map
        for (Room room : rooms) {
            minX = Math.min(minX, room.getX()) -5;
            minY = Math.min(minY, room.getY()) -5;
            maxX = Math.max(maxX, room.getX() + room.getWidth()+5);
            maxY = Math.max(maxY, room.getY() + room.getHeight()+5);
        }

        char[][] map = new char[maxX - minX + 5][maxY - minY + 5]; // Adjust dimensions according to bounds
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = '.';
            }
        }
        TileData.playerSpawn[0] = (rooms.get(0).getX()+rooms.get(0).getWidth()/2)-minX;
        TileData.playerSpawn[1] = (rooms.get(0).getY()+rooms.get(0).getHeight()/2)- minY;
        for (Room room : rooms) {


            for (int i = room.getX(); i < room.getX() + room.getWidth(); i++) {
                for (int j = room.getY(); j < room.getY() + room.getHeight(); j++) {
                    map[i - minX][j - minY] = 'F';

                }
            }
        }

        for (Corridor corridor : corridors) {
            for (int i = corridor.getX(); i < corridor.getX() + corridor.getWidth(); i++) {
                for (int j = corridor.getY(); j < corridor.getY() + corridor.getHeight(); j++) {
                    map[i - minX][j - minY] = 'F';
                }
            }
        }

        TileData[][] tiles = new TileData[map.length+10][map[0].length+10];


        try (PrintWriter writer = new PrintWriter(new FileWriter("map.txt"))) {
            // Write map to file
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    writer.print(map[i][j]);
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map[0].length; j++) {

                if (map[i][j] == '.') {
                    tiles[i][j] = new TileData("", true);

                }else if (map[i][j] == 'F') {
                    if (map[i - 1][j] == '.' && map[i][j + 1] == '.') { // top-left corner floor
                        tiles[i][j] = new TileData("stonefloor1_0_0.png", false);
                    } else if (map[i][j + 1] == '.' && map[i + 1][j] == 'F' && map[i - 1][j] == 'F') { // top floor
                        tiles[i][j] = new TileData("stonefloor1_1_0.png", false);
                    } else if (map[i + 1][j] == '.' && map[i][j + 1] == '.') { // top-right corner floor
                        tiles[i][j] = new TileData("stonefloor1_2_0.png", false);
                    } else if (map[i - 1][j] == '.' && map[i][j + 1] == 'F' && map[i][j - 1] == 'F') { // middle left
                                                                                                       // floor
                        tiles[i][j] = new TileData("stonefloor1_0_1.png", false);
                    } else if (map[i + 1][j] == '.' && map[i][j + 1] == 'F' && map[i][j - 1] == 'F') { // middle right
                                                                                                       // floor
                        tiles[i][j] = new TileData("stonefloor1_2_1.png", false);
                    } else if (map[i - 1][j] == '.' && map[i][j - 1] == '.') { // bottom-left corner floor
                        tiles[i][j] = new TileData("stonefloor1_0_2.png", false);
                    } else if (map[i][j - 1] == '.' && map[i + 1][j] == 'F' && map[i - 1][j] == 'F') { // bottom floor
                        tiles[i][j] = new TileData("stonefloor1_1_2.png", false);
                    } else if(map[i][j-1]=='.' && map[i+1][j]=='.'){ //bottom right corner floor
                        tiles[i][j] = new TileData("stonefloor1_2_2.png", false);
                    } else { //center floor
                        tiles[i][j] = new TileData("stonefloor1_1_1.png", false);
                    }

                }

            }
        }
        setWalls(map,tiles);
    return tiles; //
    }
    public static void setWalls(char[][] map, TileData[][] tiles){
        int wallType = 0;
        for (int j = 0; j < map[0].length; j++) {
            for (int i = 0; i < map.length; i++) {
                
                if(map[i][j]=='F' && map[i][j+1]=='.'){
                    map[i][j+1] = 'W';
                    map[i][j+2] = 'W';
                    map[i][j+3] = 'W';

                    tiles[i][j+1] = new TileData("stonewall1_"+wallType+"_2.png", true);
                    tiles[i][j+2] = new TileData("stonewall1_"+wallType+"_1.png", true);
                    tiles[i][j+3] = new TileData("stonewall1_"+wallType+"_0.png", true);
                    wallType++;

                    if(wallType > 3){
                        wallType = 0;
                    }
                }else{
                    wallType = 0;
                }
            }
        }
    }
}