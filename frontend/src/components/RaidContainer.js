import Raider from "./Raider";
import "../css/RaidContainer.css";
import { useState } from "react";

function RaidContainer({ raidData }) {
  const [selectedTarget, setSelectedTarget] = useState(null);
  const [mouseOverTarget, setMouseOverTarget] = useState(null);

  // Calculate the number of rows and columns based on raid size
  const raidSize = 20;

  const numRows = Math.ceil(raidSize / 5);
  const numCols = 5;

  // Create an array of row arrays, each containing up to 5 raiders
  const rows = [];
  for (let i = 0; i < numRows; i++) {
    const startIndex = i * numCols;
    const endIndex = Math.min(startIndex + numCols, raidSize);
    const row = raidData.slice(startIndex, endIndex);
    rows.push(row);
  }

  const handleMouseOver = (raider) => {
    setMouseOverTarget(raider);
  };

  const handleClick = (raider) => {
    setSelectedTarget(raider);
  };

  const handleMouseLeave = () => {
    setMouseOverTarget(null);
  };

  const getCurrentTarget = () => {
    if (mouseOverTarget) {
      return mouseOverTarget;
    } else if (selectedTarget) {
      return selectedTarget;
    } else {
      return null;
    }
  };

  return (
    <div className="raid-container">
      <div className="raid-grid">
        {rows.map((row, rowIndex) => (
          <div key={rowIndex} className="raid-row">
            {row.map((raider, colIndex) => (
              <div
                key={raider.id}
                className="raid-col"
                onClick={() => handleClick(raider)}
                onMouseOver={() => handleMouseOver(raider)}
                onMouseLeave={() => handleMouseLeave()}
              >
                <Raider raider={raider} />
              </div>
            ))}
          </div>
        ))}
      </div>
      {getCurrentTarget() && <Raider raider={getCurrentTarget()} />}
    </div>
  );
}

export default RaidContainer;
