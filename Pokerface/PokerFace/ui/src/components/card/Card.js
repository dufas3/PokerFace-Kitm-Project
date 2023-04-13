import "./Card.css";
import setUserSelectedCard from "../../api/set/setUserSelectedCard";
import {getSelectedCard, setSelectedCard} from "./SelectedCard";
import {useEffect} from "react";

const Card = ({ cardId, cardValue, userId, sessionState, roomId }) => {

  const handleOnClick = async () => {
    console.log("handle set active card invoked!");
    console.log("sessionState", sessionState);
    if (sessionState == 1) return;
    setSelectedCard(cardId)
    let response = await setUserSelectedCard({
      userId: userId,
      cardId: cardId,
      roomId: roomId,
    });
    console.log("setUserSelectedCard response", response);
  };
  useEffect(()=>{
    setSelectedCard("")
  },[sessionState])

  return (
    <div className={getSelectedCard() == cardId? "card-css selected-bg-true" : "card-css"}>
      {cardValue.length > 3 ? (
        <button className="card-button" onClick={handleOnClick}>
          <h6
            className={getSelectedCard() == cardId  ? "number-top selected-true" : "number-top"}
          >
            {cardValue}
          </h6>

          <div
            className={
              getSelectedCard() == cardId ? "card-middle selected-bg-true" : "card-middle"
            }
          >
            <h6 className={getSelectedCard() == cardId ? "number selected-true" : "number"}>
              {cardValue}
            </h6>
          </div>
          <h6
            className={
              getSelectedCard() == cardId ? "number-bottom selected-true" : "number-bottom"
            }
          >
            {cardValue}
          </h6>
        </button>
      ) : (
        <button className="card-button" onClick={handleOnClick}>
          <h5
            className={
              getSelectedCard() == cardId ? "number-top1 selected-true" : "number-top1"
            }
          >
            {cardValue}
          </h5>
          <div
            className={
              getSelectedCard() == cardId ? "card-middle selected-bg-true" : "card-middle"
            }
          >
            <h2 className={getSelectedCard() == cardId ? "number selected-true" : "number"}>
              {cardValue}
            </h2>
          </div>
          <h5
            className={
              getSelectedCard() == cardId ? "number-bottom1 selected-true" : "number-bottom1"
            }
          >
            {cardValue}
          </h5>
        </button>
      )}
    </div>
  );
};
export default Card;
