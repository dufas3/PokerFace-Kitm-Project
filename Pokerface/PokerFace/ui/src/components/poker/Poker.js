import Nav from "../header/Nav";
import "../GlobalCSS.css";
import PlayerList from "./PlayerList";
import VotingArea from "./VotingArea";
import VotingControls from "./VotingControls";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import GetCards from "../../api/get/getCards";
import getSessionState from "../../api/get/getSessionState";
import GetActiveCards from "../../api/get/getActiveCards";
import GetSessionUsers from "../../api/get/getSessionUsers";
import { MethodNames } from "../../common/methodNames";
import { signalRConnection } from "../../api/signalR/signalRHub";
import { useSearchParams } from "react-router-dom";
import getUser from "../../api/get/getUser";
import { getUserId, setUserId } from "../../common/UserId";
import setSessionState from "../../api/set/setSessionState";
import { SessionState } from "../../common/sessionState";
import getSettings from "../../api/get/getSettings";
import { SettingsType } from "../../common/settingsType";

const Poker = () => {
  const [cards, setCards] = useState([]);
  const [users, setUsers] = useState([]);
  const [user, setUser] = useState({});
  const [state, setState] = useState(0);
  const [activeCards, setActiveCards] = useState([]);
  const [roomId, setRoomId] = useState("");
  const [searchParams] = useSearchParams();
  const [navig, setNavig] = useState();
  const [settings, setSettings] = useState([]);
  const [disconnect, setDisconnect] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const setData = async () => {
      if (!signalRConnection) await signalRConnection.start();

      //api calls
      await updateSettings();
      await setUpUser();
      await getCards();
      await setUserList();
      await setSessionStateFromApi();
      await getActiveCards();
    };
    setData();
  }, []);

  useEffect(() => {
    setRoomId(searchParams.get("roomId"));
  }, [roomId]);
  useEffect(() => {
    if (getUserId() == null) {
      if (searchParams.get("roomId") === undefined) {
        setNavig(navigate("/Login", { replace: true }));
      } else {
        setNavig(
          navigate("/Join?roomId=" + searchParams.get("roomId"), {
            replace: true,
          })
        );
      }
    }
  }, []);

  useEffect(() => {
    //to force rerender when response is awaited
  }, [users]);

  const setUserList = async () => {
    let response = await GetSessionUsers({
      roomId: searchParams.get("roomId"),
    });
    if (response) setUsers(response);
  };

  const setSessionStateFromApi = async () => {
    let response = await getSessionState({
      roomId: searchParams.get("roomId"),
    });
    setState(response);
  };

  const setUpUser = async () => {
    let response = await getUser({
      userId: getUserId(),
      roomId: searchParams.get("roomId"),
    });
    setUser(response);
  };

  const getCards = async () => {
    let response = await GetCards();
    if (response) {
      setCards(response);
    }
  };

  const getActiveCards = async () => {
    let activeCardsResponse = await GetActiveCards({
      roomId: searchParams.get("roomId"),
    });
    if (activeCardsResponse) {
      setActiveCards(activeCardsResponse);
    }
  };
  const updateSettings = async () => {
    let response = await getSettings({ roomId: searchParams.get("roomId") });
    setSettings(response);
  };

  //---------------CHANGES STATE IF ALL PLAYERS VOTED---------------------
  useEffect(() => {
    let allVoted = true;
    console.log(
      "Auto reveal: ",
      settings.filter((x) => x.name === SettingsType.AutoReveal)[0]?.isActive
    );
    if (
      !settings.filter((x) => x.name === SettingsType.AutoReveal)[0]?.isActive
    )
      return;
    if (users.length > 1) {
      users.map((user) => {
        if (!user.name.includes("@")) {
          if (user.selectedCard == null) {
            allVoted = false;
          }
        }
      });
    } else {
      allVoted = false;
    }
    if (allVoted) {
      const stateChange = async () => {
        await setSessionState({
          roomId: searchParams.get("roomId"),
          state: SessionState.ALLUSERSVOTED,
        });
      };
      stateChange();
    }
  }, [users]);

  //-----------------Event Listeners-------------------------------------
  useEffect(() => {
    const getData = async () => {
      signalRConnection.on(MethodNames.PlayerListUpdate, () => {
        setUserList();
      });
    };
    getData();
  }, []);
  useEffect(() => {
    const getData = async () => {
      signalRConnection.on(MethodNames.SessionStateUpdate, () => {
        setSessionStateFromApi();
      });
    };
    getData();
  }, []);

  useEffect(() => {
    const getData = async () => {
      signalRConnection.on(MethodNames.ActiveCardsUpdate, () => {
        getActiveCards();
        updateSettings();
      });
    };
    getData();
  }, []);

  useEffect(() => {
    const getData = async () => {
      signalRConnection.on(MethodNames.SessionLogout, () => {
        setUserId(undefined);
        setNavig(navigate("/Login", { replace: true }));
      });
    };
    getData();
  }, []);
  useEffect(() => {
    const getDate = async () => {
      signalRConnection.on(MethodNames.SettingsUpdate, () => {
        updateSettings();
      });
    };
    getDate();
  }, []);
  //---------------------------------------------------------------------------
  return (
    <>
      {roomId && (
        <>
          <Nav user={user} />
          <div className="poker">
            <div className="voting">
              <VotingArea
                cards={activeCards}
                userId={user.id}
                roomId={searchParams.get("roomId")}
                sessionState={state}
                userList={users}
              />
              {user.role == "moderator" ? (
                <VotingControls
                  settings={settings}
                  cards={cards}
                  roomId={searchParams.get("roomId")}
                  activeCards={activeCards}
                  session={state}
                  key={activeCards.join(",")}
                />
              ) : (
                ""
              )}
            </div>
            <PlayerList sessionState={state} userList={users} roomId={searchParams.get("roomId")} user={user}/>
          </div>
        </>
      )}
    </>
  );
};

export default Poker;
