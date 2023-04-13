using PokerFace.Data.Entities;

namespace PokerFace.Commands.User
{
    public class UserDto
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public Card SelectedCard { get; set; }
    }
}
