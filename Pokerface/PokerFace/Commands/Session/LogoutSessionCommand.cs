using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Session
{
    public class LogoutSessionCommand : IRequest
    {
        public string RoomId { get; set; }
    }

    public class LogoutSessionCommandHandler : IRequestHandler<LogoutSessionCommand>
    {
        private readonly ISessionService sessionService;
        private readonly ISignalRService signalRService;


        public LogoutSessionCommandHandler(ISessionService sessionService, ISignalRService signalRService)
        {
            this.sessionService = sessionService;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(LogoutSessionCommand request, CancellationToken cancellationToken)
        {
            await sessionService.LogoutSessionAsync(request.RoomId);
            return Unit.Value;
        }
    }
}
