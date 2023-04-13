using System.ComponentModel.DataAnnotations;

namespace PokerFace.Data.Entities
{
    public class BaseEntity
    {
        [Key]
        public int Id { get; protected set; }
    }
}
