package com.transcend.test.numberdatabase.controller;

import com.transcend.test.numberdatabase.model.Dungeon;
import com.transcend.test.numberdatabase.service.DungeonCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DungeonController {
    DungeonCreationService dungeonMaker;

    /**
     * Main Entry point for web app, added min values to prevent stalling out on bad inputs.
     * @param RoomNumber
     * @param dungeonHeight
     * @param dungeonWidth
     * @return
     */
    @GetMapping(value ="/dungeon")
    public ResponseEntity<Dungeon> generateDungeon(@RequestParam(defaultValue = "5") int RoomNumber, @RequestParam(defaultValue = "20") int dungeonHeight, @RequestParam(defaultValue = "20") int dungeonWidth) {
        dungeonMaker = new DungeonCreationService();
        if(RoomNumber < 1){
            RoomNumber = 1;
        }
        if(dungeonHeight < 4){
            dungeonHeight = 4;
        }
        if( dungeonWidth < 4){
            dungeonWidth = 4;
        }
        Dungeon dungeon = dungeonMaker.makeDungeon(RoomNumber, dungeonHeight, dungeonWidth);
        System.out.println(dungeon.toString());
        return ResponseEntity.ok(dungeon);
    }
}

