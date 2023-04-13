using Microsoft.AspNetCore.SignalR;
using Org.BouncyCastle.Asn1.Ocsp;
using PokerFace.Data.Common;
using PokerFace.Services;

namespace PokerFace.Data.Hubs
{
    public class PokerFaceHub : Hub
    {
        private readonly IUserRepository userRepository;
        private readonly ISignalRService signalRService;
        private readonly ISessionService sessionService;

        public PokerFaceHub(IUserRepository userRepository, ISignalRService signalRService, ISessionService sessionService)
        {
            this.userRepository = userRepository;
            this.signalRService = signalRService;
            this.sessionService = sessionService;
        }

        [HubMethodName("ReceiveConnectSockets")]
        public async Task ReceiveConnectSockets(int userId, string roomId)
        {
            try
            {
                if (userId != 0)
                    await userRepository.SetSocketId(Context.ConnectionId, userId, roomId);
                else
                    await userRepository.SetSocketId(Context.ConnectionId, 0, roomId);
            }
            catch
            {
                throw new BadHttpRequestException("");
            }
        }

        public override async Task OnDisconnectedAsync(Exception? exception)
        {
            var user = await userRepository.GetAsync(Context.ConnectionId);
            if (user == null)
                return;

            if (!user.Name.Contains("@"))
            {
                await sessionService.LogoutSessionUserAsync(user.RoomId, user.Id);
                await signalRService.SendMessage(StaticHubMethodNames.SendPlayerListUpdate, user.RoomId);
            }
            //deletes session but not sends an logout update //fix
            else
            {
                await signalRService.SendMessage(StaticHubMethodNames.SessionLogout, user.RoomId);
                await sessionService.LogoutSessionAsync(user.RoomId);
            }

        }

        [HubMethodName("PlayerListUpdate")]
        public async Task SendPlayerListUpdate(string socketId)
        {
            if (Context.ConnectionId == socketId)
                await Clients.Client(socketId).SendAsync("PlayerListUpdate");
        }

        [HubMethodName("UserCardSelectionUpdate")]
        public async Task SendUserCardSelectionUpdate(string socketId)
        {
            if (Context.ConnectionId == socketId)
                await Clients.Client(socketId).SendAsync("UserCardSelectionUpdate");
        }

        [HubMethodName("ActiveCardsUpdate")]
        public async Task SendActiveCardsUpdate(string socketId)
        {
            if (Context.ConnectionId == socketId)
                await Clients.Client(socketId).SendAsync("ActiveCardsUpdate");
        }

        [HubMethodName("SessionStateUpdate")]
        public async Task SendSessionStateUpdate(string socketId)
        {
            if (Context.ConnectionId == socketId)
                await Clients.Client(socketId).SendAsync("SessionStateUpdate");
        }

        [HubMethodName("SettingsUpdate")]
        public async Task SendSettingsUpdate(string socketId)
        {
            if (Context.ConnectionId == socketId)
                await Clients.Client(socketId).SendAsync("SettingsUpdate");
        }
    }
}
