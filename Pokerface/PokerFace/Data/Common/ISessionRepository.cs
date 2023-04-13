using PokerFace.Data.SessionModels;
using SessionDb = PokerFace.Data.Entities.Session;
using ModeratorDb = PokerFace.Data.Entities.Moderator;
using SettingDb = PokerFace.Data.Entities.Setting;
using CardDb = PokerFace.Data.Entities.Card;


namespace PokerFace.Data.Common
{
    public interface ISessionRepository : IAsyncRepository<SessionDb>
    {
        Task AddAsync(ModeratorDb moderator, List<SettingDb> activeSettings, List<CardDb> activeCards);

        Task UpdateAsync(Session session);

        Task<List<User>> GetSessionUsersAsync(string roomId);
        Task<Session> GetByRoomIdAsync(string roomId);
        Task<List<Entities.Card>> GetActiveCardsAsync(string roomId);
        Task<List<Entities.Card>> GetSessionUsersSelectedCardAsync(string roomId);
        Task<SessionState> GetSessionStateAsync(string roomId);
        Task<SessionDb> GetSessionFromDb(string roomId);

        Task SetSessionStateAsync(string roomId, SessionState state);
        Task SetActiveCardsAsync(string roomId, List<int> cardIds);

        Task RemoveSession(string roomId);
        Task UpdateTimeStampAsync(string roomId, DateTime time);
        Task SetLastTimerAsync(string roomId);
        Task<DateTime> GetLastTimerAsync(string roomId);
    }
}
