import ConnectionUrl from "../../common/connectionUrl";

const GetConnectionId = async ({ userId, roomId }) => {
  if (!userId) return;

  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  const url = ConnectionUrl({ appendix: "/user/getModerator" });
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
export default GetConnectionId;
