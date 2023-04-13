import ConnectionUrl from "../common/connectionUrl";

const AddToSession = async ({ name, roomId, socketId }) => {
  if (!name) return;

  const requestOptions = {
    method: "Post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      name: name,
      roomId: roomId,
      socketId: socketId,
    }),
  };

  const url = ConnectionUrl({ appendix: "/user/addToSession" });

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
export default AddToSession;
