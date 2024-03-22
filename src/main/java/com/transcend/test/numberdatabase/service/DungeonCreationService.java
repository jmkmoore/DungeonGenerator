package com.transcend.test.numberdatabase.service;

import com.transcend.test.numberdatabase.model.Dungeon;
import com.transcend.test.numberdatabase.model.RoomBaseTile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class DungeonCreationService {

    public Dungeon makeDungeon(int numOfRooms, int dungeonHeight, int dungeonWidth){
        Dungeon dungeon = new Dungeon(dungeonWidth, dungeonHeight, numOfRooms);

        int maxRoomDimension = (dungeonHeight*dungeonWidth)/(numOfRooms * numOfRooms);
        System.out.println("Max Dimensions: " + maxRoomDimension);
        int roomCount = 0;

        /**
         * Set up base room tiles
         */
        while(roomCount < numOfRooms) {
            int floorX = (int) (Math.random() * (dungeonWidth)) % dungeonWidth;
            int floorY = (int) (Math.random() * (dungeonHeight)) % dungeonHeight;
            if(dungeon.isValidRoom(floorX,floorY)) {
                dungeon.addRoom(floorX, floorY);
                roomCount++;
            }
        }


        /**
         * Connect rooms together
         */
        dungeon.connectRooms();

        /**
         * Build Paths in dungeon to other rooms
         */

        HashMap<RoomBaseTile, Boolean> connectedRooms;
        for(RoomBaseTile roomBaseTile: dungeon.getKnownRooms()){
            connectedRooms = roomBaseTile.getConnectedTiles();
            for(RoomBaseTile tile: connectedRooms.keySet()) {
                if (!roomBaseTile.getConnectedTiles().get(tile)) {
                    dungeon.buildPath(roomBaseTile.getX(), roomBaseTile.getY(), tile.getX(), tile.getY());
                }
            }
        }

        return dungeon;
    }


}
