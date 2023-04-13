import ConnectionUrl from "../../common/connectionUrl";

const SetUserSelectedCard = async ({ userId, cardId, roomId }) => {
  if (!userId || !cardId || !roomId) return;
  const requestOptions = {
    method: "Post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      userId: userId,
      cardId: cardId,
      roomId: roomId,
    }),
  };

  const url = ConnectionUrl({ appendix: "/user/SetSelectedCard" });
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
export default SetUserSelectedCard;
