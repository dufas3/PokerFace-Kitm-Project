import ConnectionUrl from "../../common/connectionUrl";

const SetSettings = async ({ roomId, ids }) => {

    const requestOptions = {
        method: "Post",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            roomId: roomId,
            ids: ids,
        }),
    };

    const url = ConnectionUrl({ appendix: "/settings/setSettings" });
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
export default SetSettings;