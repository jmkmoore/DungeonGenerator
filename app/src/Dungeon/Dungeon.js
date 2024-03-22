import React, {useEffect, useState} from 'react';
import DungeonTile from '../DungeonTile/DungeonTile'
import {Button} from "reactstrap";

export default function Dungeon() {

    const [dungeon, setDungeon] = useState("");
    const [roomCount, setRoomCount] = useState(5);
    const [dungeonWidth, setDungeonWidth] = useState(50);
    const [dungeonHeight, setDungeonHeight] = useState(50);

    useEffect(() => {
        fetchDungeon();
    }, []);


    const fetchDungeon = async() => {
            const response = await fetch(`api/dungeon?RoomNumber=${roomCount}&dungeonHeight=${dungeonHeight}&dungeonWidth=${dungeonWidth}`)
                .then(response => response.json())
                .then(data => {
                    setDungeon(data);
                    setDungeon(false);
                })
    };

        let map = dungeon.dungeonFloor;
        let tileMap = []

        for (var i = 0; i < map.length; i++) {
            var rowMap = map[i];
            for (var j = 0; j < rowMap.length; j++) {
                tileMap.push(<DungeonTile tileValue={rowMap[j]}/>)

            }
        }

        return (
            <>
                <form>
                    Enter Dungeon Width: <input defaultValue={50} type="text" pattern="[0-9]*"
                                                value={dungeonWidth}
                                                onChange={e => setDungeonWidth(e.target.value)} min="4"/><br/>
                    Enter Dungeon Height: <input defaultValue={50} type="text" pattern="[0-9]*"
                                                 value={dungeonHeight}
                                                 onChange={e => setDungeonHeight(e.target.value)} min="4"/><br/>

                    Enter Number of Rooms: <input defaultValue={5} type="text" pattern="[0-9]*"
                                                  value={roomCount}
                                                  onChange={e => setRoomCount(e.target.value)} min="1"/><br/>

                    <Button onClick={fetchDungeon}>Generate Dungeon</Button>
                </form>
                {tileMap}
            </>
    );
}
