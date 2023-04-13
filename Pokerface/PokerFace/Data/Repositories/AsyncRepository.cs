using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Data.Repositories
{
    public class AsyncRepository<T> : IAsyncRepository<T> where T : BaseEntity
    {
        private ApplicationDbContext context;

        public AsyncRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public async Task<T> GetAsync(int id)
        {
            return await context.FindAsync<T>(id);
        }

        public async Task<List<T>> GetRangeAsync(List<int> ids)
        {
            var items = new List<T>();
            foreach (var id in ids)
            {
                var item = await context.FindAsync<T>(id);
                if (item != null)
                    items.Add(item);
            }
            return items;
        }

        public async Task AddAsync(T entity)
        {
            await context.AddAsync(entity);
            await context.SaveChangesAsync();
        }

        public async Task UpdateAsync(T entity)
        {
            context.Update(entity);
            await context.SaveChangesAsync();
        }

        public async Task UpdateRangeAsync(ICollection<T> entities)
        {
            context.RemoveRange(entities);
            await context.SaveChangesAsync();
        }

        public async Task DeleteAsync(T entity)
        {
            context.Remove(entity);
            await context.SaveChangesAsync();
        }
    }
}
