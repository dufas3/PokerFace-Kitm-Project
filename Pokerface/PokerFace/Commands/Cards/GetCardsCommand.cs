using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Commands.Cards
{
    public class GetCardsCommand : IRequest<List<Card>>
    {        

    }

    public class GetCardsCommandHandler : IRequestHandler<GetCardsCommand, List<Card>>
    {
        private readonly ICardsRepository cardsRepository;

        public GetCardsCommandHandler(ICardsRepository cardsRepository)
        {
            this.cardsRepository = cardsRepository;
        }

        public async Task<List<Card>> Handle(GetCardsCommand request, CancellationToken cancellationToken)
        {
            return await cardsRepository.GetCardsAsync();
        }
    }
}
