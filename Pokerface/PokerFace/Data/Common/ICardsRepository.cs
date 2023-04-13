using PokerFace.Data.Entities;

namespace PokerFace.Data.Common
{
    public interface ICardsRepository : IAsyncRepository<Card>
    {
        Task<List<Card>> GetCardsAsync();
        Task<Card> GetCardAsync(int id);
    }
}
