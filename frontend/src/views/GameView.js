import Api from "../api/Api";
import {useEffect, useState} from "react";
import Raider from "../components/Raider";
import RaidContainer from "../components/RaidContainer";

function GameView() {
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            const data = await Api("state.json");
            setData(data);
        }
        fetchData();

    }, [])

    return (
        <div>
            {data &&
                <RaidContainer raidData={data}/>
            }
            {data && <pre>{JSON.stringify(data, null, 2)}</pre>}
        </div>
    );
}

export default GameView;
