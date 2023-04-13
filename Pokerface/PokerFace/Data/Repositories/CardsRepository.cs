using Microsoft.EntityFrameworkCore;
using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Data.Repositories
{
    public class CardsRepository : AsyncRepository<Card>, ICardsRepository
    {
        private readonly ApplicationDbContext context;

        public CardsRepository(ApplicationDbContext context) : base(context)
        {
            this.context = context;
        }

        public async Task<Card> GetCardAsync(int id)
        {
            return await Task.Run(() => StaticSessionData.AllCards.FirstOrDefault(x => x.Id == id));
        }

        public async Task<List<Card>> GetCardsAsync()
        {
            return await context.Cards.ToListAsync();
        }
    }
}
