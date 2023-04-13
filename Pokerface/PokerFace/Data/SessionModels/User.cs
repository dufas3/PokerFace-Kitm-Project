namespace PokerFace.Data.SessionModels
{
    public class User
    {
        public int Id { get; set; }
        public string RoomId { get; set; }
        public string? Name { get; set; }
        public int? SelectedCardId { get; set; }
        public string ConnectionId { get; set; }
    }
}
