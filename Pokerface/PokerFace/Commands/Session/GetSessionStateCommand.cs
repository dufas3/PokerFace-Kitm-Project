using MediatR;
using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class GetSessionStateCommand : IRequest<SessionState>
    {
        public string RoomId { get; set; }
    }

    public class GetSessionStateCommandHanlder : IRequestHandler<GetSessionStateCommand, SessionState>
    {
        private readonly ISessionRepository sessionRepository;

        public GetSessionStateCommandHanlder(ISessionRepository sessionRepository)
        {
            this.sessionRepository = sessionRepository;
        }

        public async Task<SessionState> Handle(GetSessionStateCommand request, CancellationToken cancellationToken)
        {
            return await sessionRepository.GetSessionStateAsync(request.RoomId);
        }
    }
}
