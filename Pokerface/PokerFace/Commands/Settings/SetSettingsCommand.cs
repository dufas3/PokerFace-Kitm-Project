using MediatR;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;
using PokerFace.Data.Hubs;

namespace PokerFace.Commands.Settings
{
    public class SetSettingsCommand : IRequest
    {
        public List<int> Ids { get; set; }
        public string RoomId { get; set; }

    }
    public class SetSettingsCommandHandler : IRequestHandler<SetSettingsCommand>
    {

        private readonly ISignalRService signalRService;
        private readonly ISettingsRepository settingsRepository;

        public SetSettingsCommandHandler(ISettingsRepository settingsRepository, ISignalRService signalRService)
        {
            this.settingsRepository = settingsRepository;
            this.signalRService = signalRService;
        }

        public async Task<Unit> Handle(SetSettingsCommand request, CancellationToken cancellationToken)
        {
            await settingsRepository.SetSettingsAsync(request.Ids, request.RoomId);
            await signalRService.SendMessage(StaticHubMethodNames.SettingsUpdate, request.RoomId);
            return Unit.Value;
        }
    }
}
