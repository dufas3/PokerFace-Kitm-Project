using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Commands.Session
{
    public class GetUserSelectedCardsCommand : IRequest<List<Card>>
    {
        public string RoomId { get; set; }
    }

    public class GetUserSelectedCardsHandler : IRequestHandler<GetUserSelectedCardsCommand, List<Card>>
    {
        private readonly ISessionRepository sessionRepository;

        public GetUserSelectedCardsHandler(ISessionRepository sessionRepository)
        {
            this.sessionRepository = sessionRepository;
        }

        public async Task<List<Card>> Handle(GetUserSelectedCardsCommand request, CancellationToken cancellationToken)
        {
            return await sessionRepository.GetSessionUsersSelectedCardAsync(request.RoomId);
        }
    }
}
