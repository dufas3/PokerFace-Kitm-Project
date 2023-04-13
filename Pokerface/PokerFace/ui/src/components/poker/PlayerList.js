import './PlayerList.css'
import '../GlobalCSS.css'
import "react-toastify/dist/ReactToastify.css";
import UserIcon from "../../imgs/user.png";
import VoteIcon from "../../imgs/vote-icon.png";
import Notifications from "../notifications/Notifications";
import { useEffect, useState } from "react";
import { SessionState } from "../../common/sessionState";
import { useSearchParams } from "react-router-dom";
import React from 'react';
import SetLastTimer from '../../api/set/setLastTimer';
import GetLastTimer from '../../api/get/getLastTimer';

const PlayerList = ({ sessionState, userList, roomId, user }) => {
    const [users, setUsers] = useState([]);
    const [state, setState] = useState(20);
    const [userCount, setUserCount] = useState(0);
    const [listText, setListText] = useState("");
    const [time, setTime] = useState({ s: 0, m: 0, h: 0 });
    const [interv, setInterv] = useState();
    let updatedS = time.s, updatedM = time.m, updatedH = time.h;

    const setUserList = async () => {
        setUserCount(userList.length - 1);
    };

    useEffect(() => {
        setUserList();
        setState(sessionState);
    }, [userList, sessionState]);

    useEffect(() => {
        if (user?.role === undefined) return;
        if (user?.role !== "moderator") {
            const GetData = async () => {
                let response = await GetLastTimer({ roomId: roomId });
                let date = new Date(Date.now() - new Date(response));
                setTime({
                    s: date.getSeconds(),
                    m: date.getMinutes(),
                    h: date.getHours() - 3,
                });
                updatedS = date.getSeconds();
                updatedM = date.getMinutes();
                updatedH = date.getHours() - 3;
            };
            GetData();
        }
    }, [user])



    useEffect(() => {
        if (userCount <= 0) {
            setListText("Waiting for players");
        } else {
            if (state == SessionState.VOTESTATE) {
                setListText("Waiting for " + userCount + " players to vote");
            } else if (state == SessionState.FINISHSTATE) {
                setListText("Waiting for " + userCount + " players to vote");
            } else if (state == SessionState.FINILIZESTATE || state == SessionState.ALLUSERSVOTED) {
                setListText("Waiting for moderator to finalize votes");
            }
        }

        setUsers(userList)
    }, [state, userList])

    useEffect(() => {
        if (state == SessionState.VOTINGSTART || state == SessionState.VOTESTATE) {
            if (user?.role === "moderator")
                SetLastTimer({
                    roomId: roomId,
                })
            clearInterval(interv);
            setInterv(setInterval(run, 1000));
        } else if (state == SessionState.FINILIZESTATE || state == SessionState.ALLUSERSVOTED) {
            clearInterval(interv);
            setTime({ s: 0, m: 0, h: 0 });
        }
    }, [state, user]);


    const run = () => {
        if (updatedM === 60) {
            updatedH++;
            updatedM = 0;
        }
        if (updatedS === 60) {
            updatedM++;
            updatedS = 0;
        }
        updatedS++;
        setTime({ s: updatedS, m: updatedM, h: updatedH });
    };


    return (
        <div className="player-list border rounded bg-light">
            <div className="player-list-top">
            <h6 className="bg-primary border border-primary rounded-top w-100 p-4 text-center text-white margin-0">
                {listText}
            </h6>
            <div className="align-center-between p-1 text-dark border-bottom bg-light">
                <h6 className="mt-1">
                    Players:
                </h6>
                <h6 className="mt-1">
                    <i class="bi bi-clock px-2" />
                    <span>{(time.h >= 10) ? time.h : "0" + time.h}</span>:
                    <span>{(time.m >= 10) ? time.m : "0" + time.m}</span>:
                    <span>{(time.s >= 10) ? time.s : "0" + time.s}</span>
                </h6>
            </div>
            </div>
            <div className="users">
            {users.map((user) => (
                <div className="border-bottom user align-center-between">
                    <div className="align-center-start">
                        <div className="icon m-lg-1 align-center">
                            {user.name.includes("@")
                                ? <><img src={UserIcon} className="user-icon" /><i
                                    className="fa-solid fa-circle moderator-indicator"></i></> :
                                <><img src={UserIcon} className="user-icon" /><i
                                    className="fa-solid fa-circle user-indicator"></i></>}
                        </div>
                        <h6>
                            {user.name}
                            {user.cardId != "0"
                                ? user.name.includes("@")
                                    ? ""
                                    : user.selectedCard && (
                                        <i className="fa-regular fa-circle-check text-primary"></i>
                                    )
                                : ""}
                        </h6>
                    </div>
                    {user.name.includes("@") ? (
                        ""
                    ) : state == SessionState.ALLUSERSVOTED || state == SessionState.FINILIZESTATE ? (
                        <h5
                            className={
                                user.selectedCard == null
                                    ? "vote-icon"
                                    : user.selectedCard.value.length > 3
                                        ? "vote-icon out-of-border"
                                        : "vote-icon"
                            }
                        >
                            {user.selectedCard == null ? "?" : user.selectedCard.value}
                        </h5>
                    ) : (
                        <img src={VoteIcon} className="vote-icon" />
                    )}
                </div>
            ))}
            </div>
            <Notifications data={{ state: sessionState }} />
        </div>
    );
};

export default PlayerList;
