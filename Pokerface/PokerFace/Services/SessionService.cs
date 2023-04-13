using PokerFace.Data.Common;
using PokerFace.Data.Entities;
using PokerFace.Commands.Email;
using PokerFace.Data;

namespace PokerFace.Services
{
    public class SessionService : ISessionService
    {
        private readonly ISessionRepository sessionRepository;
        private readonly IUserRepository userRepository;
        private readonly IEmailSender _emailSender;
        private readonly ICardsRepository cardsRepository;
        private readonly ISettingsRepository settingsRepository;

        public SessionService(ISessionRepository sessionRepository, IUserRepository userRepository, IEmailSender emailSender, ICardsRepository cardsRepository, ISettingsRepository settingsRepository)
        {
            this.sessionRepository = sessionRepository;
            this.userRepository = userRepository;
            _emailSender = emailSender;
            this.cardsRepository = cardsRepository;
            this.settingsRepository = settingsRepository;
        }

        public async Task CreateSession(Moderator moderator)
        {
            //creates session in staticSesionData (every logout it deletes from it), and creates in db if there isnt
            Session? fromDbSession = await sessionRepository.GetSessionFromDb(moderator.RoomId);
            bool isSessionNew = fromDbSession == null;
            var allSettings = await settingsRepository.GetSettingsAsync();

            if (isSessionNew)
            {
                moderator.RoomId = Guid.NewGuid().ToString();
                //adding to db
                await sessionRepository.AddAsync(new Session { ModeratorId = moderator.Id, RoomId = moderator.RoomId, LastLogin = DateTime.Now, ActiveSettings = allSettings });
                await userRepository.UpdateModeratorAsync(moderator);
            }

            await sessionRepository.AddAsync(moderator, isSessionNew ? allSettings : fromDbSession.ActiveSettings, fromDbSession?.ActiveCards ?? new List<Card>());

            StaticSessionData.AllCards = await cardsRepository.GetCardsAsync();

            var link = "https://pokerface-dev.azurewebsites.net/?roomId=" + moderator.RoomId;
            var message = new Message(new string[] { moderator.Name }, "FESTO Scrum Poker", "Dear Moderator,\n\nYou have created new voting room, its unique link is: " + link + "\nPlease use it to access this room. This link can be shared with other players to access the same room.");
            await _emailSender.SendEmailAsync(message);
        }

        public async Task LogoutSessionAsync(string roomId)
        {
            var dbSession = await sessionRepository.GetSessionFromDb(roomId);
            var session = await sessionRepository.GetByRoomIdAsync(roomId);

            dbSession.ActiveSettings = session.Settings;

            var cards = dbSession?.ActiveCards ?? new List<Card>();
            cards = await cardsRepository.GetRangeAsync(session.CardIds);
            dbSession.ActiveCards = cards;

            await sessionRepository.UpdateAsync(dbSession);
            await sessionRepository.RemoveSession(roomId);
        }

        public async Task LogoutSessionUserAsync(string roomId, int userId)
        {
            var user = await userRepository.GetAsync(userId, roomId);
            await userRepository.DeleteAsync(user);
        }

        public async Task ClearVotes(string roomId)
        {
            var users = await sessionRepository.GetSessionUsersAsync(roomId);

            users.ForEach(x => x.SelectedCardId = null);

            await StaticSessionData.SaveChangesAsync(users, roomId);
        }
    }
}
