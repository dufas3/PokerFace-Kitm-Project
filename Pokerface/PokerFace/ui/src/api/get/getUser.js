import ConnectionUrl from "../../common/connectionUrl";

const GetUser = async ({ userId, roomId }) => {
  let user;

  if (!userId || !roomId) return;
  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  const url = ConnectionUrl({ appendix: "/user/getUser" });
  url.searchParams.append("roomId", roomId);
  url.searchParams.append("userId", userId);

  try {
    const response = await fetch(url.toString(), requestOptions);

    const isJson = response.headers
      .get("content-type")
      ?.includes("application/json");

    const data = isJson && (await response.json());

    user = {
      id: data.id,
      name: data.name,
      role: data.name.includes("@") ? "moderator" : "user",
      selectedCard: data.selectedCard,
    };

    return user;
  } catch (error) {
    return;
  }
};

export default GetUser;
