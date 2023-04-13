using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Commands.User
{
    public class GetSelectedCardCommand : IRequest<Card>
    {
        public int UserId { get; set; }
        public string RoomId { get; set; }
    }

    public class GetSelectedCardCommandHanlder : IRequestHandler<GetSelectedCardCommand, Card>
    {
        private readonly IUserRepository userRepository;

        public GetSelectedCardCommandHanlder(IUserRepository userRepository)
        {
            this.userRepository = userRepository;
        }

        public async Task<Card> Handle(GetSelectedCardCommand request, CancellationToken cancellationToken)
        {
            return await userRepository.GetSelectedCardAsync(request.UserId, request.RoomId);
        }
    }
}
