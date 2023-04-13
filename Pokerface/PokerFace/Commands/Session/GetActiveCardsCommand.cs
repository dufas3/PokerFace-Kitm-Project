using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Commands.Session
{
    //active session cards
    public class GetActiveCardsCommand : IRequest<List<Card>>
    {
        public string RoomId { get; set; }
    }

    public class GetActiveCardsCommandHandler : IRequestHandler<GetActiveCardsCommand, List<Card>>
    {
        private readonly ISessionRepository sessionRepository;

        public GetActiveCardsCommandHandler(ISessionRepository sessionRepository)
        {
            this.sessionRepository = sessionRepository;
        }

        public async Task<List<Card>> Handle(GetActiveCardsCommand request, CancellationToken cancellationToken)
        {
            return await sessionRepository.GetActiveCardsAsync(request.RoomId);
        }
    }
}
