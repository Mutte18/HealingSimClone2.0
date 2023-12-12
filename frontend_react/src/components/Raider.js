import '../css/Raider.css';
function Raider({ raider}) {
    const { id, health, maxHealth, alive, buffs, hpInPercent } = raider;

    const healthBarWidth = (health / maxHealth) * 100;
    const healthBarStyle = {
        width: `${healthBarWidth}%`,
    };

    return(
        <div className="raider-frame">
            <div className="raider-info">
                <div className="raider-name">{id}</div>
                <div className="raider-status">{alive ? 'Alive' : 'Dead'}</div>
                <div className="raider-health-bar">
                    <div className="raider-health-bar-fill" style={healthBarStyle}>
                        <div className="raider-health-bar-text">{health}/{maxHealth}</div>
                    </div>
                </div>
                <div className="raider-hp-in-percent">{hpInPercent}%</div>


            </div>
        </div>
    );
}

export default Raider;
