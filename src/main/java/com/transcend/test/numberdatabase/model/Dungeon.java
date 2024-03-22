package com.transcend.test.numberdatabase.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
public class Dungeon {
    String[][] dungeonFloor;
    int numberOfRooms;
    int width;
    int height;
    ArrayList<RoomBaseTile> knownRooms;
    private Dungeon(){}

    public Dungeon(int dungeonWidth, int dungeonHeight, int roomCount){
        dungeonFloor = new String[dungeonHeight][dungeonWidth+1];
        for(int i = 0; i < dungeonHeight; i++){
            for(int j = 0; j < dungeonWidth+1; j++){
                if(j == dungeonWidth){
                    dungeonFloor[i][j] = "|";
                }
                else {
                    dungeonFloor[i][j] = ".";
                }
            }
        }
        width = dungeonWidth;
        height = dungeonHeight;
        numberOfRooms = roomCount;
        knownRooms = new ArrayList<>(numberOfRooms);
    }

    /**
     * Checkes whether indicated tile is inside dimensions of dungeon
     * @param x
     * @param y
     * @return
     */
    public boolean isValidRoom(int x, int y) {
        if((x <= width && x > 0) && (y < height && y > 0))
            return (!dungeonFloor[y][x].equals("|"));
        return false;
    }

    public boolean setTile(int x, int y, String symbol){
        if(dungeonFloor[y][x].equals(".")){
            dungeonFloor[y][x] = symbol;
            return true;
        } else
            return false;
    }

    /**
     * Adds a room to the list of rooms known by the dungeon
     * @param x
     * @param y
     * @return
     */
    public RoomBaseTile addRoom(int x, int y){
        RoomBaseTile room = new RoomBaseTile(x, y);
        knownRooms.add(new RoomBaseTile(x, y));
        dungeonFloor[y][x] = "*";
        return room;
    }

    /**
     * Tells the rooms which rooms they are connected to
     */
    public void connectRooms(){
        for(int h = 0; h < knownRooms.size(); h++){
            if(knownRooms.get(h).connectedTiles.keySet().size() > 3){
                break;
            }
            double connectionChance = .5;
            for(int i = 0; i < knownRooms.size(); i++) {
                if (h != i){
                    if(!(knownRooms.get(i).getX() == knownRooms.get(h).getX() && knownRooms.get(i).getY() == knownRooms.get(h).getY())){
                        if (Math.random() > connectionChance) {
                            knownRooms.get(h).addRoomBaseConnection(knownRooms.get(i));
                        } else {
                            connectionChance += .05;
                        }
                    }
                }
            }
            if(knownRooms.get(h).getConnectedTiles().size() < 3) {
                knownRooms.get(h).addRoomBaseConnection(knownRooms.get((h + 1) % knownRooms.size()));
                knownRooms.get(h).addRoomBaseConnection(knownRooms.get((h + 2) % knownRooms.size()));
                if (h == 0) {
                    knownRooms.add(knownRooms.get(knownRooms.size() -1 ));
                } else {
                    knownRooms.get(h).addRoomBaseConnection(knownRooms.get((h - 1) % knownRooms.size()));
                }
            }
        }
    }

    /**
     * Random-esque path building, helps expand rooms by building out wide with some variance to path building
     * @param tile1X
     * @param tile1Y
     * @param tile2X
     * @param tile2Y
     */
    public void buildPath(int tile1X, int tile1Y, int tile2X, int tile2Y){
        if(tile1X < 0){
            tile1X = 0;
        }
        if(tile1X >= width){
            tile1X = width-1;
        }
        if(tile1Y < 0 ){
            tile1Y = 0;
        }
        if(tile1Y >= height){
            tile1Y = height-1;
        }

        if(!dungeonFloor[tile1Y][tile1X].equals("*")){
            dungeonFloor[tile1Y][tile1X] = "o";
        }
        if(tile1X == tile2X && tile1Y == tile2Y){
            return;
        }

        if(tile1X > tile2X && tile1Y > tile2Y) {
            if (Math.random() > .5) {
                buildPath(tile1X - 1, tile1Y, tile2X, tile2Y);
            } else {
                buildPath(tile1X, tile1Y - 1, tile2X, tile2Y);
            }
        }

        else if (tile1X > tile2X && tile1Y < tile2Y){
            if (Math.random() > .5) {
                buildPath(tile1X - 1, tile1Y, tile2X, tile2Y);
            } else {
                buildPath(tile1X, tile1Y +1, tile2X, tile2Y);
            }
        }

        else if(tile1X <= tile2X && tile1Y > tile2Y) {
            if (Math.random() > .5) {
                buildPath(tile1X + 1, tile1Y, tile2X, tile2Y);
            } else {
                buildPath(tile1X, tile1Y - 1, tile2X, tile2Y);
            }
        }

        else if (tile1X <= tile2X && tile1Y < tile2Y){
            if (Math.random() > .5) {
                buildPath(tile1X + 1, tile1Y, tile2X, tile2Y);
            } else {
                buildPath(tile1X, tile1Y +1, tile2X, tile2Y);
            }
        }
    }

    public void buildNonRandomPath(int tile1X, int tile1Y, int tile2X, int tile2Y){
        if(tile1X < 0){
            tile1X = 0;
        }
        if(tile1X >= width){
            tile1X = width-1;
        }
        if(tile1Y < 0 ){
            tile1Y = 0;
        }
        if(tile1Y >= height){
            tile1Y = height-1;
        }

        if(!dungeonFloor[tile1Y][tile1X].equals("*")){
            dungeonFloor[tile1Y][tile1X] = "o";
        }
        if(tile1X == tile2X && tile1Y == tile2Y){
            return;
        }

        if(tile1X == tile2X){
            if(tile1Y < tile2Y){
                buildNonRandomPath(tile1X, tile1Y + 1, tile2X, tile2Y);
                return;
            } else {
                buildNonRandomPath(tile1X, tile1Y - 1, tile2X, tile2Y);
                return;
            }
        }

        if(tile1Y == tile2Y){
            if(tile1X < tile2X){
                buildNonRandomPath(tile1X + 1, tile1Y , tile2X, tile2Y);
                return;
            } else {
                buildNonRandomPath(tile1X  - 1, tile1Y , tile2X, tile2Y);
                return;
            }
        }

        if(tile1X > tile2X) {
            buildNonRandomPath(tile1X - 1, tile1Y, tile2X, tile2Y);
        }

        if (tile1X < tile2X){
            buildNonRandomPath(tile1X + 1, tile1Y, tile2X, tile2Y);
        }

        if(tile1Y > tile2Y) {
            buildNonRandomPath(tile1X, tile1Y - 1, tile2X, tile2Y);
        }

        if (tile1Y < tile2Y){
            buildNonRandomPath(tile1X, tile1Y + 1, tile2X, tile2Y);
        }


    }

    public void fillRoom(RoomBaseTile startTile){
        for(int i = startTile.getY() - 2; i < startTile.getY() +2; i++){
            for(int j = startTile.getX() -2; j < startTile.getX() + 2; j++){
                if(isValidRoom(i,j)) {
                    dungeonFloor[i][j] = "*";
                }
            }
        }
    }

    public String toString(){
        String output = "";
        for (int i = 0; i < dungeonFloor.length; i++) {
            for (String tile : dungeonFloor[i]) {
                output += tile;
            }
            output += System.lineSeparator();
        }
        output = "Dungeon floor:\n"+output+"";
        return output;
    }
}
