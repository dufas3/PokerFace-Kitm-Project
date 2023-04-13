import ConnectionUrl from "../../common/connectionUrl";

const SetActiveUserCard = async ({ roomId, userId, cardId }) => {
  if (!roomId || !userId || !cardId) return;

  const requestOptions = {
    method: "Post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      userId: userId,
      cardId: cardId,
      roomId: roomId,
    }),
  };

  const url = ConnectionUrl({ appendix: "/card/SetActiveUserCard" });
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
export default SetActiveUserCard;
