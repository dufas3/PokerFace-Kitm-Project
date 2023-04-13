import * as signalR from "@microsoft/signalr";
import ConnectionUrl from "../../common/connectionUrl";

const url = ConnectionUrl({ appendix: "/pokerFaceHub" }).toString();

//call this when connectionId in local storage is not matching with connectionId in db SetConnectionId({ value: connection.connectionId });

// const CreateConnection = async ({ userId, methodName }) => {
//   const connection = new signalR.HubConnectionBuilder().withUrl(url).build();

//   await connection.start();

//   if (methodName == MethodNames.ReceiveConnectSockets) {
//     if (userId) await connection.invoke(methodName, userId.toString());
//     else await connection.invoke(methodName, "");
//   } else return connection;
// };

const connection = new signalR.HubConnectionBuilder()
  .withUrl(url)
  .withAutomaticReconnect()
  .build();

export const signalRConnection = connection;

export const GetConnectionIdFromLocal = () => {
  return localStorage.getItem("connectionId");
};
