using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Session
{
    public class SetSessionStateCommand : IRequest
    {
        public string RoomId { get; set; }
        public SessionState State { get; set; }
    }

    public class SetSessionStateCommandHanlder : IRequestHandler<SetSessionStateCommand>
    {
        private readonly ISessionRepository sessionRepository;
        private readonly ISignalRService signalRService;

        public SetSessionStateCommandHanlder(ISessionRepository sessionRepository, ISignalRService signalRService)
        {
            this.sessionRepository = sessionRepository;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(SetSessionStateCommand request, CancellationToken cancellationToken)
        {
            await sessionRepository.SetSessionStateAsync(request.RoomId, request.State);

            await signalRService.SendMessage(StaticHubMethodNames.SendSessionStateUpdate, request.RoomId);

            return Unit.Value;
        }
    }
}
