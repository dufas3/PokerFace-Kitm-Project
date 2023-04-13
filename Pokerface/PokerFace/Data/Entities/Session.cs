namespace PokerFace.Data.Entities
{
    public class Session : BaseEntity
    {
        public string RoomId { get; set; }
        public int ModeratorId { get; set; }
        public DateTime LastLogin { get; set; }

        //redo activity logic
        public List<Card> ActiveCards { get; set; } //only active is here
        public List<Setting> ActiveSettings { get; set; } //all settings, active is one with isActive flag
    }
}
