package com.transcend.test.numberdatabase.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;

@Getter
@Setter
public class RoomBaseTile {
    int x = 0;
    int y = 0;
    HashMap<RoomBaseTile, Boolean> connectedTiles;

    private RoomBaseTile(){}

    public RoomBaseTile(int x, int y){
        this.x = x;
        this.y = y;
        connectedTiles = new HashMap<>();
    }

    public void addRoomBaseConnection(RoomBaseTile otherRoom){
        if(!connectedTiles.containsKey(otherRoom)) {
            connectedTiles.put(otherRoom, false);
            otherRoom.addRoomBaseConnection(this);
        }
    }
}
