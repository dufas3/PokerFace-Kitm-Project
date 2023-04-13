using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Session
{
    public class SetActiveCardsCommand : IRequest
    {
        public string RoomId { get; set; }
        public List<int> CardIds { get; set; }
    }

    public class SetActiveCardsCommandHandler : IRequestHandler<SetActiveCardsCommand>
    {
        private readonly ISessionRepository sessionRepository;
        private readonly ISignalRService signalRService;

        public SetActiveCardsCommandHandler(ISessionRepository sessionRepository, ISignalRService signalRService)
        {
            this.sessionRepository = sessionRepository;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(SetActiveCardsCommand request, CancellationToken cancellationToken)
        {
            await sessionRepository.SetActiveCardsAsync(request.RoomId, request.CardIds);

            await signalRService.SendMessage(StaticHubMethodNames.SendActiveCardsUpdate, request.RoomId);

            return Unit.Value;
        }
    }
}
