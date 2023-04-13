using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Session
{
    public class LogoutUserCommand : IRequest
    {
        public int UserId { get; set; }
        public string RoomId { get; set; }
    }

    public class LogoutUserCommandHandler : IRequestHandler<LogoutUserCommand>
    {
        private readonly ISessionService sessionService;
        private readonly ISignalRService signalRService;

        public LogoutUserCommandHandler(ISessionService sessionService, ISignalRService signalRService)
        {
            this.sessionService = sessionService;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(LogoutUserCommand request, CancellationToken cancellationToken)
        {
            await sessionService.LogoutSessionUserAsync(request.RoomId, request.UserId);
            await signalRService.SendMessage(StaticHubMethodNames.SendPlayerListUpdate, request.RoomId);
            return Unit.Value;
        }
    }
}
