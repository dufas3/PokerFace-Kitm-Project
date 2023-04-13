import ConnectionUrl from "../../common/connectionUrl";

const GetModerator = async ({ email, password }) => {
  if (!email || !password) return;

  const requestOptions = {
    method: "Get",
    headers: { "Content-Type": "application/json" },
  };

  const url = ConnectionUrl({ appendix: "/user/getModerator" });
  url.searchParams.append("userEmail", email);
  url.searchParams.append("userPassword", password);

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
export default GetModerator;
