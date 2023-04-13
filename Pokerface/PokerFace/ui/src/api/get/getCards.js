import ConnectionUrl from "../../common/connectionUrl";

const GetCards = async () => {
  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  const url = ConnectionUrl({ appendix: "/card/getCards" });

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

export default GetCards;
