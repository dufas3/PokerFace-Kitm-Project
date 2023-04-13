using MediatR;
using PokerFace.Commands.User;
using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class GetSessionUsersCommand : IRequest<List<UserDto>>
    {
        public string RoomId { get; set; }
    }

    public class GetSessionUsersCommandHandler : IRequestHandler<GetSessionUsersCommand, List<UserDto>>
    {
        private readonly ISessionRepository sessionRepository;
        private readonly ICardsRepository cardsRepository;

        public GetSessionUsersCommandHandler(ISessionRepository sessionRepository, ICardsRepository cardsRepository)
        {
            this.sessionRepository = sessionRepository;
            this.cardsRepository = cardsRepository;
        }

        public async Task<List<UserDto>> Handle(GetSessionUsersCommand request, CancellationToken cancellationToken)
        {
            var users = await sessionRepository.GetSessionUsersAsync(request.RoomId);

            var userDtos = users.Select(x => x.ToDto()).ToList();

            foreach (var userDto in userDtos)
            {
                var user = users.FirstOrDefault(x => x.Id == userDto.Id);

                if (user.SelectedCardId != null)
                {
                    userDto.SelectedCard = await cardsRepository.GetCardAsync(user.SelectedCardId.Value);
                }
            }

            return userDtos;
        }
    }
}
