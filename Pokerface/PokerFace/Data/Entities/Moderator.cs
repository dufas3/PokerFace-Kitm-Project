namespace PokerFace.Data.Entities
{
    public class Moderator : BaseEntity
    {
        public string? RoomId { get; set; }
        public string? Name { get; set; }
        public string? Password { get; set; }
        public string? ConnectionId { get; set; }
    }
}
