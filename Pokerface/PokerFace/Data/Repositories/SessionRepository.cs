using Microsoft.EntityFrameworkCore;
using PokerFace.Data.Common;
using PokerFace.Data.SessionModels;
using SessionDb = PokerFace.Data.Entities.Session;
using ModeratorDb = PokerFace.Data.Entities.Moderator;
using CardDb = PokerFace.Data.Entities.Card;
using SettingDb = PokerFace.Data.Entities.Setting;

namespace PokerFace.Data.Repositories
{
    public class SessionRepository : AsyncRepository<SessionDb>, ISessionRepository
    {
        private readonly ApplicationDbContext context;

        public SessionRepository(ApplicationDbContext context) : base(context)
        {
            this.context = context;
        }

        public async Task<List<User>> GetSessionUsersAsync(string roomId)
        {
            return await StaticSessionData.GetSessionUsersAsync(roomId);
        }

        public async Task<Session> GetByRoomIdAsync(string roomId)
        {
            return await StaticSessionData.GetSessionAsync(roomId);
        }

        public async Task AddAsync(ModeratorDb moderator, List<SettingDb> activeSettings, List<CardDb> activeCards)
        {
            var settings = await context.Settings.ToListAsync();
            var cards = await context.Cards.ToListAsync();

            var session = await context.Sessions.FirstOrDefaultAsync(x => x.RoomId == moderator.RoomId);

            StaticSessionData.AddSession(moderator, session.Id, settings, activeCards, cards);
        }

        public async Task<List<CardDb>> GetSessionUsersSelectedCardAsync(string roomId)
        {
            var users = await StaticSessionData.GetSessionUsersAsync(roomId);

            var cards = new List<CardDb>();

            foreach (var user in users)
            {
                if (user.SelectedCardId == null)
                    continue;

                var card = StaticSessionData.AllCards.Where(x => x.Id == user.SelectedCardId).FirstOrDefault();
                cards.Add(card);
            }

            return cards;
        }

        public async Task<SessionState> GetSessionStateAsync(string roomId)
        {
            var session = await GetByRoomIdAsync(roomId);

            if (session == null)
                throw new BadHttpRequestException("No session by that id");

            return session.State;
        }

        public async Task SetSessionStateAsync(string roomId, SessionState state)
        {
            var session = await GetByRoomIdAsync(roomId);

            session.State = state;

            await StaticSessionData.SaveChangesAsync(session, roomId);
        }

        public async Task SetActiveCardsAsync(string roomId, List<int> cardIds)
        {
            var session = await GetByRoomIdAsync(roomId);

            session.CardIds.Clear();
            session.CardIds.AddRange(cardIds);

            await StaticSessionData.SaveChangesAsync(session, roomId);
        }

        public async Task<List<CardDb>> GetActiveCardsAsync(string roomId)
        {
            var session = await GetByRoomIdAsync(roomId);

            var activeCards = new List<CardDb>();

            foreach (var id in session.CardIds)
            {
                var card = StaticSessionData.AllCards.Where(x => x.Id == id).FirstOrDefault();
                if (card != null)
                    activeCards.Add(card);
            }

            return activeCards;
        }

        public async Task RemoveSession(string roomId)
        {
            await StaticSessionData.RemoveSessionAsync(roomId);
        }

        public async Task UpdateTimeStampAsync(string roomId, DateTime time)
        {
            var session = await context.Sessions.FirstOrDefaultAsync(x => x.RoomId == roomId);
            session.LastLogin = time;
            await context.SaveChangesAsync();
        }

        public async Task<SessionDb> GetSessionFromDb(string roomId)
        {
            return await context.Sessions.Include(x => x.ActiveCards).Include(x => x.ActiveSettings).FirstOrDefaultAsync(x => x.RoomId == roomId);
        }

        public async Task UpdateAsync(Session session)
        {
            await StaticSessionData.SaveChangesAsync(session, session.RoomId);
        }

        public async Task SetLastTimerAsync(string roomId)
        {
            var session = await StaticSessionData.GetSessionAsync(roomId);
            session.LastTimer = DateTime.UtcNow;
        }

        public async Task<DateTime> GetLastTimerAsync(string roomId)
        {
            var session = await StaticSessionData.GetSessionAsync(roomId);
            return session.LastTimer;
        }
    }
}
