using MediatR;
using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class GetSessionCommand : IRequest
    {
        public string RoomId { get; set; }
    }

    public class GetSessionCommandHandler : IRequestHandler<GetSessionCommand>
    {
        private readonly ISessionRepository sessionRepository;

        public GetSessionCommandHandler(ISessionRepository sessionRepository)
        {
            this.sessionRepository = sessionRepository;
        }

        public async Task<Unit> Handle(GetSessionCommand request, CancellationToken cancellationToken)
        {
             if(await sessionRepository.GetByRoomIdAsync(request.RoomId) != null )
                return Unit.Value;
            throw new BadHttpRequestException("No room by that Id");
        }
    }
}
