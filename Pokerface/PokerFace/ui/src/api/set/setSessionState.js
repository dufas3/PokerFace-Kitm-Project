import ConnectionUrl from "../../common/connectionUrl";

const SetSessionState = async ({ roomId, state }) => {

  const requestOptions = {
    method: "Post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      roomId: roomId,
      state: state,
    }),
  };

  const url = ConnectionUrl({ appendix: "/session/SetSessionState" });
  try {
    const response = await fetch(url.toString(), requestOptions);
    const isJson = response.headers
      .get("content-type")
      ?.includes("application/json");
    const data = isJson && (await response.json());
    return data;
  } catch (error) {
    return;
  }
};
export default SetSessionState;
