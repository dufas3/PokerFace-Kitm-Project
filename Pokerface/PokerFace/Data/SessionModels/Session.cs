using PokerFace.Data.Common;
using PokerFace.Data.Entities;

namespace PokerFace.Data.SessionModels
{
    public class Session
    {
        public int Id { get; set; }
        public int RefId { get; set; }//id in db
        public string RoomId { get; set; }
        public int ModeratorId { get; set; }
        public DateTime LastTimer { get; set; } //for timer sinchonization
        public List<int> CardIds { get; } = new List<int>();//active cards
        public SessionState State { get; set; }
        public List<Setting> Settings { get; set; }//only active settings
    }
}
