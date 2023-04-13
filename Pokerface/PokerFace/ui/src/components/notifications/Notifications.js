import { ToastContainer, toast } from "react-toastify";
import { useEffect, useState } from "react";
import { SessionState } from "../../common/sessionState";

const Notifications = (props) => {
  const [firstLoad, setFirstLoad] = useState(0);
  let message = "";

  const notify = (props) =>
    toast.info(props.message, {
      position: "top-center",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "colored",
    });
  useEffect(() => {
    if (firstLoad >= 1) {
      if (props.data.state == SessionState.VOTESTATE) {
        message = "Votes are cleared by the moderator. Voting re-started";
      } else if (props.data.state == SessionState.FINILIZESTATE) {
        message = "Moderator flipped the cards. Voting stopped.";
      } else if (props.data.state == SessionState.FINISHSTATE) {
          toast.info("Voting finished", {
              position: "top-center",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
              theme: "colored",
          })
          message = "Voting starts.";
      } else if (props.data.state == SessionState.ALLUSERSVOTED) {
        message = "Voting stopped.";
      } else if (props.data.state == SessionState.VOTINGSTART) {
        message = "Voting starts.";
      } else if (props.data.state == SessionState.NONECARDSSELECTED) {
        message = "No cards selected";
      }
      notify({ message: message });
    } else {
      setFirstLoad(firstLoad + 1);
    }
  }, [props.data.state]);
  return <></>;
};
export default Notifications;
