namespace PokerFace.Data.SessionModels
{
    public class SessionData
    {
        public string RoomId { get; set; }
        public Session Session { get; set; }
        public List<User> Users { get; set; } //session users
    }
}
