namespace PokerFace.Data
{
    public static class SessionDataExtensions
    {
        public static SessionModels.User ToSessionDataUser(this Entities.Moderator user)
        {
            return user == null ? new SessionModels.User() : new SessionModels.User
            {
                Id = user.Id,
                ConnectionId = user.ConnectionId,
                Name = user.Name,
                RoomId = user.RoomId
            };
        }
    }
}
