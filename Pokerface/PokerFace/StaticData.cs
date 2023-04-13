using PokerFace.Data;
using PokerFace.Data.Entities;

namespace PokerFace
{
    public class StaticData
    {
        public StaticData(ApplicationDbContext context)
        {
            if (context.Moderators.Count() != 0 && context.Cards.Count() != 0)
                return;
           
            context.Moderators.Add(new Moderator { Name = "testemail@gmail.com", Password = "testpassword123" });

            var cards = new List<Card>
            {
                new Card
                {
                    Value = "0",
                    IsActive = true
                },
                new Card
                {
                    Value = "1/2",
                    IsActive = true
                },
                new Card
                {
                    Value = "1",
                    IsActive = true
                },
                new Card
                {
                    Value = "2",
                    IsActive = true
                },
                new Card
                {
                    Value = "3",
                    IsActive = true
                },
                new Card
                {
                    Value = "5",
                    IsActive = true
                },
                new Card
                {
                    Value = "8",
                    IsActive = true
                },
                new Card
                {
                    Value = "13",
                    IsActive = true
                }
                ,new Card
                {
                    Value = "20",
                    IsActive = true
                },
                new Card
                {
                    Value = "40",
                    IsActive = true
                },
                new Card
                {
                    Value = "100",
                    IsActive = true
                },
                new Card
                {
                    Value = "?",
                    IsActive = true
                },
                new Card
                {
                    Value = "Coffee",
                    IsActive = true
                }
            };
            context.Cards.AddRange(cards);

            context.SaveChanges();
        }
    }
}
