import ConnectionUrl from "../../common/connectionUrl";

const GetActiveCards = async ({ roomId }) => {
  if (!roomId) return;

  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  const url = ConnectionUrl({ appendix: "/card/getActiveCards" });
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

export default GetActiveCards;
