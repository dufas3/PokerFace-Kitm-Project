
using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Commands.Settings
{
    public class GetSettingsCommand : IRequest <List<Setting>>
    {
        public string RoomId { get; set; }
    }

    public class GetSettingsCommandHandler : IRequestHandler<GetSettingsCommand, List<Setting>>
    {

        private readonly ISettingsRepository settingsRepository;

        public GetSettingsCommandHandler(ISettingsRepository settingsRepository)
        {
            this.settingsRepository = settingsRepository;
        }
        public async Task<List<Setting>> Handle(GetSettingsCommand request, CancellationToken cancellationToken)
        {
            return await settingsRepository.GetSettingsAsync(request.RoomId);
        }
    }

}
