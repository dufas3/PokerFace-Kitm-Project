import ConnectionUrl from "../../common/connectionUrl";

const SetLastTimer = async ({ roomId }) => {

    const requestOptions = {
        method: "Post",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            roomId: roomId,
        }),
    };

    const url = ConnectionUrl({ appendix: "/session/setLastTimer" });
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
export default SetLastTimer;