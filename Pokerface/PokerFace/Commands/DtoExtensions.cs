using PokerFace.Commands.User;

namespace PokerFace.Commands
{
    public static class DtoExtensions
    {
        public static UserDto ToDto(this Data.SessionModels.User user)
        {
            return new UserDto
            {
                Id = user.Id,
                Name = user.Name
            };
        }

        public static ModeratorDto ToDto(this Data.Entities.Moderator moderator)
        {
            return new ModeratorDto
            {
                UserId = moderator.Id,
                Email = moderator.Name,
                RoomId = moderator.RoomId        
            };
        }
    }
}
