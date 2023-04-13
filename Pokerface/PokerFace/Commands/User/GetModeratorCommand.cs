using MediatR;
using PokerFace.Data.Common;

namespace PokerFace.Commands.User
{
    public class GetModeratorCommand : IRequest<ModeratorDto>
    {
        public string UserEmail { get; set; }
        public string UserPassword { get; set; }
    }

    public class GetModeratorCommandHandler : IRequestHandler<GetModeratorCommand, ModeratorDto>
    {
        private readonly IUserRepository userRepository;
        private readonly ISessionService sessionService;

        public GetModeratorCommandHandler(IUserRepository userRepository, ISessionService sessionService)
        {
            this.userRepository = userRepository;
            this.sessionService = sessionService;
        }

        public async Task<ModeratorDto> Handle(GetModeratorCommand request, CancellationToken cancellationToken)
        {
            var user = await userRepository.GetModeratorAsync(request.UserEmail, request.UserPassword);
            await sessionService.CreateSession(user);

            return user.ToDto();
        }
    }
}
