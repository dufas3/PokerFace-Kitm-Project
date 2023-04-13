import ConnectionUrl from "../../common/connectionUrl";

const GetUserSelectedCard = async ({ roomId, userId }) => {
  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  if (!roomId || userId) return;

  const url = ConnectionUrl({ appendix: "/user/getSelectedCard" });
  url.searchParams.append("userId", userId);
  url.searchParams.append("roomId", roomId);

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
export default GetUserSelectedCard;
