using PokerFace.Data.Entities;

namespace PokerFace.Data.Common
{
    public interface ISessionService
    {
        public Task CreateSession(Moderator moderator);
        Task LogoutSessionAsync(string roomId);
        Task LogoutSessionUserAsync(string roomId, int userId);
        Task ClearVotes(string roomId);
    }
}
