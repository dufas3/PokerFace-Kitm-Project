using MediatR;
using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class GetLastTimerCommand : IRequest<DateTime>
    {
        public string RoomId { get; set; }
    }
    public class GetLastTimerCommandHandler : IRequestHandler<GetLastTimerCommand, DateTime>
    {
        private readonly ISessionRepository sessionRepository;
        public GetLastTimerCommandHandler(ISessionRepository sessionRepository)
        {
            this .sessionRepository = sessionRepository;
        }
        public async Task<DateTime> Handle(GetLastTimerCommand request, CancellationToken cancellationToken)
        {
            return await sessionRepository.GetLastTimerAsync(request.RoomId);
        }
    }
}
