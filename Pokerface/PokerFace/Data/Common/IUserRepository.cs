using PokerFace.Data.Entities;
using PokerFace.Data.SessionModels;

namespace PokerFace.Data.Common
{
    public interface IUserRepository
    {
        Task<User> GetAsync(int id, string roomId);
        Task<User> GetAsync(string socketId);
        Task<Entities.Moderator> GetModeratorAsync(string email, string password);
        Task<Entities.Card> GetSelectedCardAsync(int userId, string roomId);

        Task SetSocketId(string socketId, int userId, string roomId);
        Task SetSelectedCardAsync(int userId, int cardId, string roomId);

        Task AddUserToSessionAsync(User user);
        Task UpdateModeratorAsync(Moderator moderator);
        Task UpdateAsync(User user);

        Task DeleteAsync(User user);
    }
}
