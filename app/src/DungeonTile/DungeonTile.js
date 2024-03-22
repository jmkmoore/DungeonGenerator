
export default function DungeonTile({tileValue}) {
    var val = "";


    if(tileValue === "|"){
        val = <br/>
    } else if (tileValue === ".") {
        val = "◼"
    } else if (tileValue === "*") {
        val = "▣"
    }else
    {
        val = "◻";
    }

    return (
        <>
            {val}
        </>
)};