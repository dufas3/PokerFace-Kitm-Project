import "./VotingControls.css";
import "../GlobalCSS.css";
import { useEffect, useState } from "react";
import { SessionState } from "../../common/sessionState";
import setSessionState from "../../api/set/setSessionState";
import ClearSessionVotes from "../../api/clearSessionVotes";
import SetActiveCards from "../../api/set/setActiveCards";
import SetSettings from "../../api/set/setSettings";

const VotingControls = ({ cards, activeCards, roomId, settings }) => {
  const [inSettings, setInSettings] = useState(false);
  const [selectedCheckboxes, setSelectedCheckboxes] = useState([]);
  const [isCheckAll, setIsCheckAll] = useState(false);
  const [settingsCheckBox, setSettingsCheckbox] = useState([]);

  useEffect(() => {
    setUpAlreadyActiveCards();
  }, [activeCards]);

  useEffect(() => {
    setUpAlreadyActiveSettings();
  }, [settings]);

  const handleSetAllCards = (e) => {
    setIsCheckAll(!isCheckAll);
    setSelectedCheckboxes(cards.map((li) => li.id.toString()));
    if (isCheckAll) {
      setSelectedCheckboxes([]);
    }
  };

  const handleFlipCards = async () => {
    await setSessionState({
      roomId: roomId,
      state: SessionState.FINILIZESTATE,
    });
  };

  const handleFinish = async () => {
    await setSessionState({
      roomId: roomId,
      state: SessionState.FINISHSTATE,
    });
    await ClearSessionVotes({ roomId: roomId });
  };

  const handleClearVotes = async () => {
    await setSessionState({
      roomId: roomId,
      state: SessionState.VOTESTATE,
    });
    await ClearSessionVotes({ roomId: roomId });
  };

  const handleSettingsCheckboxChange = (e, id) => {
    if (e) {
      const { value, checked } = e.target;
      console.log("value", value);
      setSettingsCheckbox([...settingsCheckBox, id]);
      if (!checked) {
        setSettingsCheckbox(settingsCheckBox.filter((item) => item !== value));
      } else {
        setSettingsCheckbox([...settingsCheckBox, value]);
      }
    }
  };

  const handleCheckboxChange = (e, id) => {
    if (e) {
      const { value, checked } = e.target;
      setSelectedCheckboxes([...selectedCheckboxes, id]);
      if (!checked) {
        setSelectedCheckboxes(
          selectedCheckboxes.filter((item) => item !== value)
        );
      } else {
        setSelectedCheckboxes([...selectedCheckboxes, value]);
      }
    }
  };

  const handleChangeCardsSave = async () => {
    let ids = [];
    selectedCheckboxes.map((cb) => {
      if (!ids.includes(cb)) ids.push(cb);
    });
    ids.sort((a, b) => a - b);

    if (ids.length == 0) {
      await setSessionState({
        roomId: roomId,
        state: SessionState.NONECARDSSELECTED,
      });
    } else {
      await setSessionState({
        roomId: roomId,
        state: SessionState.VOTINGSTART,
      });
      setInSettings(false);

      await SetActiveCards({
        roomId: roomId,
        cardIds: ids,
      });
      await SetSettings({ roomId: roomId, ids: settingsCheckBox });
      await ClearSessionVotes({ roomId: roomId });
    }
  };

  const handleChangeCardsCancel = async () => {
    if (inSettings) {
      setInSettings(false);
    } else {
      setInSettings(true);
    }
  };

  const setUpAlreadyActiveCards = async () => {
    activeCards?.map((card) => {
      handleCheckboxChange(undefined, card.id.toString());
      setSelectedCheckboxes(activeCards.map((li) => li.id.toString()));
    });
  };

  const setUpAlreadyActiveSettings = async () => {
    settings?.map((setting) => {
      if (setting.isActive) {
        handleSettingsCheckboxChange(undefined, setting.id.toString());
        setSettingsCheckbox(settings.map((li) => li.id.toString()));
      }
    });
  };

  return (
    <>
      {!inSettings ? (
        <div className="voting-container border rounded bg-light">
          <button
            onClick={handleChangeCardsCancel}
            className="settings-button btn btn-light"
          >
            <i className="fa-solid fa-gear settings"></i>
          </button>
          <div className="center-contents">
            <div className="voting-buttons">
              <div className="row-top">
                <button
                  className="flip-cards btn btn-outline-primary"
                  onClick={handleFlipCards}
                >
                  <h6 className="center-text">Flip Cards</h6>
                </button>
                <button
                  className="clear-votes btn btn-outline-primary"
                  onClick={handleClearVotes}
                >
                  <h6 className="center-text">Clear Votes</h6>
                </button>
              </div>
              <button
                className="finish-voting btn btn-primary"
                onClick={handleFinish}
              >
                <h6 className="center-text">Finish Voting</h6>
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div className="voting-container border rounded bg-light">
          <div className="cards-select">
            <div className="form-check bg-light">
              <input
                className="form-check-input"
                type="checkbox"
                value=""
                id="flexCheckDefault"
                checked={
                  selectedCheckboxes.length >= cards?.length ? true : false
                }
                onChange={handleSetAllCards}
              />
              <label className="form-check-label" htmlFor="flexCheckDefault">
                Use all cards
              </label>
            </div>
            {cards.map((card) => (
              <div className="form-check bg-light">
                <input
                  onChange={handleCheckboxChange}
                  value={card.id.toString()}
                  className="form-check-input"
                  type="checkbox"
                  checked={selectedCheckboxes.includes(card.id.toString())}
                  id={card.value}
                />
                <label className="form-check-label" htmlFor={card.value}>
                  {card.value}
                </label>
              </div>
            ))}
          </div>
          <div className="settings">
            {settings.map((setting) => (
              <div className="">
                <input
                  className="form-check-input "
                  type="checkbox"
                  value={setting.id}
                  id={setting.name}
                  checked={settingsCheckBox.includes(setting.id.toString())}
                  onChange={handleSettingsCheckboxChange}
                />
                <label className="form-check-label ml-5" htmlFor={setting.name}>
                  {setting.name}
                </label>
              </div>
            ))}
          </div>
          <div className="exit-settings w-50">
            <button
              onClick={handleChangeCardsSave}
              className="w-25 btn btn-primary"
            >
              <h6 className="center-text">Save</h6>
            </button>
            <button
              onClick={handleChangeCardsCancel}
              className="w-25 btn btn-outline-primary"
            >
              <h6 className="center-text">Cancel</h6>
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default VotingControls;
