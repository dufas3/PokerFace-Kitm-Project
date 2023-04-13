import ConnectionUrl from "../common/connectionUrl";

const LogoutUser = async ({ roomId, userId }) => {
  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  if (!roomId || !userId) return;

  const url = ConnectionUrl({ appendix: "/session/LogoutUser" });
  url.searchParams.append("roomId", roomId);
  url.searchParams.append("userId", userId);

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
export default LogoutUser;
