import ConnectionUrl from "../../common/connectionUrl";

const GetSessionUsers = async ({ roomId }) => {
  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  if (!roomId) return;

  const url = ConnectionUrl({ appendix: "/session/getSessionUsers" });
  url.searchParams.append("roomId", roomId);

  try {
    const response = await fetch(url.toString(), requestOptions);
    const isJson = response.headers
      .get("content-type")
      ?.includes("application/json");
    const data = isJson && (await response.json());
    console.log("returning data", data);
    return data;
  } catch (error) {
    return;
  }
};
export default GetSessionUsers;
