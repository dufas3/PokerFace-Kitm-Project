namespace PokerFace.Data.Entities
{
    public class Card : BaseEntity
    {
        public string Value { get; set; }
        public bool IsActive { get; set; }
    }
}
