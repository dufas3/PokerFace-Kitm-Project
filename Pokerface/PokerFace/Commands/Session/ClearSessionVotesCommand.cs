using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Session
{
    public class ClearSessionVotesCommand : IRequest
    {
        public string RoomId { get; set; }
    }

    public class ClearVotesCommandHandler : IRequestHandler<ClearSessionVotesCommand>
    {
        private readonly ISessionService sessionService; 
        private readonly ISignalRService signalRService;

        public ClearVotesCommandHandler(ISessionService sessionService, ISignalRService signalRService)
        {
            this.sessionService = sessionService;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(ClearSessionVotesCommand request, CancellationToken cancellationToken)
        {
            await signalRService.SendMessage(StaticHubMethodNames.SendPlayerListUpdate, request.RoomId);
            await sessionService.ClearVotes(request.RoomId);
            return Unit.Value;
        }
    }
}
