using Microsoft.EntityFrameworkCore;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Data.Repositories
{
    public class SettingsRepository : AsyncRepository<Setting>, ISettingsRepository
    {
        private ApplicationDbContext context;

        public SettingsRepository(ApplicationDbContext context) : base(context)
        {
            this.context = context;
        }

        public async Task<List<Setting>> GetSettingsAsync(string roomId)
        {
            var session = await StaticSessionData.GetSessionAsync(roomId);
            return session.Settings;
        }

        public async Task<List<Setting>> GetSettingsAsync()
        {
            return await context.Settings.ToListAsync();
        }

        public async Task SetSettingsAsync(List<int> ids, string roomId)
        {
            var session = await StaticSessionData.GetSessionAsync(roomId);

            foreach (Setting setting in session.Settings)
            {
                if (ids.Contains(setting.Id)) setting.IsActive = true;
                else setting.IsActive = false;
            }

            await StaticSessionData.SaveChangesAsync(session, roomId);
        }
    }
}
