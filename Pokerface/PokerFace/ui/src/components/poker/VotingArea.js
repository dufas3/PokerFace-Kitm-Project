import "./Poker.css";
import Card from "../card/Card";
import VotingSummary from "./VotingSummary";
import "./VotingArea.css";
import "../GlobalCSS.css";
import { SessionState } from "../../common/sessionState";
import { useEffect, useState } from "react";


const VotingArea = ({ sessionState, roomId, cards, userId, userList }) => {
  const [summary, setSummary] = useState(false);
  useEffect(() => {
    if (SessionState.FINILIZESTATE == sessionState) {
      setSummary(true)
    } else if (SessionState.ALLUSERSVOTED == sessionState) {
      setSummary(true)
    } else {
      setSummary(false)
    }
  }, [sessionState])
  
  return (
    <div className="voting-area border rounded bg-light">
      { !summary ? (
        <div className="voting-area">
          <div className="cards-container">
            {cards.map((card) =>
                <Card
                  cardValue={card.value}
                  cardId={card.id}
                  userId={userId}
                  sessionState={sessionState}
                  roomId={roomId}
                />
            )}
          </div>
        </div>
      ) : (
        <VotingSummary data={{ users: userList }} />
      )}
    </div>
  );
};

export default VotingArea;
