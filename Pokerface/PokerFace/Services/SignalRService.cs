using Microsoft.AspNetCore.SignalR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Services
{
    public class SignalRService : ISignalRService
    {
        private readonly ISessionRepository sessionRepository;
        private readonly IHubContext<PokerFaceHub> hubContext;

        public SignalRService(ISessionRepository sessionRepository, IHubContext<PokerFaceHub> hubContext)
        {
            this.sessionRepository = sessionRepository;
            this.hubContext = hubContext;
        }

        public async Task SendMessage(string methodName, string roomId)
        {
            var users = await sessionRepository.GetSessionUsersAsync(roomId);

            var ids = users.Select(x => x.ConnectionId).ToList();

            foreach (var connId in ids)
            {
                await hubContext.Clients.Client(connId).SendCoreAsync(methodName,new string[] {connId});
            }
        }

        public async Task SendMessage(string methodName, string roomId, string args)
        {
            var users = await sessionRepository.GetSessionUsersAsync(roomId);

            var ids = users.Select(x => x.ConnectionId).ToList();

            foreach (var connId in ids)
            {
                await hubContext.Clients.Client(connId).SendCoreAsync(methodName, new string[] { connId, args });
            }
        }
    }
}
