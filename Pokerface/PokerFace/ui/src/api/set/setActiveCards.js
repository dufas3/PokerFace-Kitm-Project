import ConnectionUrl from "../../common/connectionUrl";

const SetActiveCards = async ({ cardIds, roomId }) => {
  if (!cardIds || !roomId) {
    return;
  }

  const requestOptions = {
    method: "Post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      roomId: roomId,
      cardIds: cardIds,
    }),
  };

  const url = ConnectionUrl({ appendix: "/session/setActiveCards" });
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
export default SetActiveCards;
