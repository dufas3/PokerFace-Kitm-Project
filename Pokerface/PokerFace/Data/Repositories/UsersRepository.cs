using Microsoft.EntityFrameworkCore;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;
using PokerFace.Data.SessionModels;
using CardDb = PokerFace.Data.Entities.Card;

namespace PokerFace.Data.Repositories
{
    public class UserRepository : IUserRepository
    {
        private ApplicationDbContext context { get; set; }

        public UserRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public async Task<User> GetAsync(int id, string roomId)
        {
            var user = await StaticSessionData.GetSessionUserAsync(roomId, id);
            if (user == null)
            {
                var temp = await context.Moderators.FirstOrDefaultAsync(x => x.Id == id);
                user = temp.ToSessionDataUser();
            }
            return user;
        }

        public async Task UpdateAsync(User user)
        {
            await StaticSessionData.SaveChangesAsync(user);
        }

        public async Task AddUserToSessionAsync(User user)
        {
            await StaticSessionData.SaveChangesAsync(user);
        }

        public async Task SetSelectedCardAsync(int userId, int cardId, string roomId)
        {
            var user = await StaticSessionData.GetSessionUserAsync(roomId, userId);

            if (user == null)
                throw new BadHttpRequestException("There's no user with this Id!");

            user.SelectedCardId = cardId;
            await StaticSessionData.SaveChangesAsync(user);
        }

        public async Task<CardDb> GetSelectedCardAsync(int userId, string roomId)
        {
            var user = await StaticSessionData.GetSessionUserAsync(roomId, userId);
            return await Task.Run(() => StaticSessionData.AllCards.Where(x => x.Id == user.SelectedCardId).FirstOrDefault());
        }

        public async Task SetSocketId(string socketId, int userId, string roomId)
        {
            var user = await StaticSessionData.GetSessionUserAsync(roomId, userId);
            bool isNew = user == null;

            if (isNew)
                user = new User();

            user.ConnectionId = socketId;
            user.RoomId = roomId;

            await StaticSessionData.SaveChangesAsync(user);
        }

        public async Task<User> GetAsync(string ConnectionId)
        {
            var sessionDatas = await Task.Run(() => StaticSessionData.SessionData.Select(x => x.Value));
            return await Task.Run(() => sessionDatas.SelectMany(sd => sd.Users).FirstOrDefault(x => x.ConnectionId == ConnectionId));
        }

        public async Task<Moderator> GetModeratorAsync(string email, string password)
        {
            var user = await context.Moderators.Where(x => x.Name == email && x.Password == password).FirstOrDefaultAsync();
            if (user == null)
                throw new BadHttpRequestException("No moderator by those credentials");
            return user;
        }

        public async Task UpdateModeratorAsync(Moderator moderator)
        {
            context.Moderators.Update(moderator);
            await context.SaveChangesAsync();
        }

        public async Task DeleteAsync(User user)
        {
            await StaticSessionData.RemoveSessionUserAsync(user);
        }
    }
}
