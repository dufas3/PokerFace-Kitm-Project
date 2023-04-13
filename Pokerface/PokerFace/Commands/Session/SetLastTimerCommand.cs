using MediatR;
using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class SetLastTimerCommand : IRequest
    {
        public string RoomId { get; set; }
    }
    public class SetLastTimerCommandHandler : IRequestHandler<SetLastTimerCommand>
    {
        private readonly ISessionRepository sessionRepository;
        public SetLastTimerCommandHandler(ISessionRepository sessionRepository)
        {
            this .sessionRepository = sessionRepository;
        }
        public async Task<Unit> Handle(SetLastTimerCommand request, CancellationToken cancellationToken)
        {
            await sessionRepository.SetLastTimerAsync (request.RoomId);
            return Unit.Value;
        }
    }
}
