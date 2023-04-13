using PokerFace.Data.Entities;

namespace PokerFace.Data.Common
{
    public interface IAsyncRepository<T> where T : BaseEntity
    {
        Task<T> GetAsync(int id);
        Task AddAsync(T entity);
        Task UpdateAsync(T entity);
        Task UpdateRangeAsync(ICollection<T> entities);
        Task DeleteAsync(T entity); 
        Task<List<T>> GetRangeAsync(List<int> ids);
    }
}
