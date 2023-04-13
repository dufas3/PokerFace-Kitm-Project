using PokerFace.Data.Common;

namespace PokerFace.Commands.Session
{
    public class SessionDto
    {
        public List<Data.SessionModels.User> Users { get; } = new List<Data.SessionModels.User>();
        public List<Data.Entities.Card> Cards { get; } = new List<Data.Entities.Card>();
        public SessionState State { get; set; }
    }
}
