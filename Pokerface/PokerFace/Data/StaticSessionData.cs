using PokerFace.Data.SessionModels;
using System.Collections.Concurrent;

namespace PokerFace.Data
{
    public static class StaticSessionData
    {
        public static ConcurrentDictionary<string, SessionData> SessionData { get; private set; } = new ConcurrentDictionary<string, SessionData>();
        public static List<Entities.Card> AllCards { get; set; } = new List<Entities.Card>();

        public static async Task<Session> GetSessionAsync(string roomId)
        {
            var obj = await Task.FromResult(SessionData[roomId].Session);

            if (obj == null)
                throw new BadHttpRequestException("No session by that id");

            return obj;
        }

        public static async Task<List<User>> GetSessionUsersAsync(string roomId)
        {
            if (SessionData.ContainsKey(roomId)) return await Task.FromResult(SessionData[roomId].Users);
            throw new BadHttpRequestException("No Session");
        }

        public static async Task<User> GetSessionUserAsync(string roomId, int id)
        {
            var a = SessionData[roomId];
            if (SessionData.ContainsKey(roomId)) return await Task
                    .FromResult(SessionData[roomId].Users
                    .Where(x => x.Id == id)
                    .FirstOrDefault());
            throw new BadHttpRequestException("No Session");
        }

        public static async Task RemoveSessionUserAsync(User user)
        {
            var sessionData = SessionData[user.RoomId];
            var index = sessionData.Users.FindIndex(x => x.Id == user.Id);
            sessionData.Users.RemoveAt(index);
            await SaveChangesAsync(sessionData.Users, user.RoomId);
        }

        public static async Task RemoveSessionAsync(string roomId)
        {
            await Task
                .Run(() => SessionData
                .TryRemove(SessionData
                .Where(x => x.Key == roomId)
                .FirstOrDefault()));
        }

        public static void AddSession(
            Entities.Moderator moderator,
            int sessionIdDb,
            List<Data.Entities.Setting> settings,
            List<Data.Entities.Card> activeCards,
            List<Data.Entities.Card> allCards)
        {
            var sessions = SessionData.Select(x => x.Value.Session).ToList();

            AllCards = allCards;

            var sd = new SessionData
            {
                RoomId = moderator.RoomId,
                Session = new Session { ModeratorId = moderator.Id, RefId = sessionIdDb, Settings = settings, LastTimer = DateTime.UtcNow},
                Users = new List<User> { moderator.ToSessionDataUser() }
            };

            sd.Session.CardIds.AddRange(activeCards.Select(x => x.Id).ToList());

            int maxId = 0;
            for (int i = 0; i < sessions.Count; i++)
                if (sessions[i].Id > maxId)
                    maxId = sessions[i].Id;
            sd.Session.Id = maxId + 1;

            try
            {
                SessionData.TryAdd(sd.RoomId, sd);
            }
            catch { }
        }

        public static async Task SaveChangesAsync(Session data, string roomId)
        {
            var sd = new SessionData { RoomId = roomId, Session = data, Users = SessionData[roomId].Users };
            await Task
               .Run(() => SessionData
               .AddOrUpdate(roomId, sd, (key, oldValue) => sd));
        }

        //only adds new users here
        public static async Task SaveChangesAsync(User data)
        {
            var roomId = data.RoomId;
            var users = new List<User>();

            users = SessionData[roomId].Users;

            if (data.Id == 0)
            {
                data.Id = await Task.Run(() => users.Max(u => u.Id));
                data.Id += 1;
                users.Add(data);
            }
            else
                for (int i = 0; i < users.Count; i++)
                    if (users[i].Id == data.Id)
                    {
                        users[i] = data;
                        break;
                    }

            var sd = new SessionData { RoomId = roomId, Session = SessionData[roomId].Session, Users = users };
            await Task
                .Run(() => SessionData
                .AddOrUpdate(roomId, sd, (key, oldValue) => sd));
        }

        public static async Task SaveChangesAsync(List<User> data, string roomId)
        {
            var users = SessionData[roomId].Users = data;

            var sd = new SessionData { RoomId = roomId, Session = SessionData[roomId].Session, Users = users };

            await Task
               .Run(() => SessionData
               .AddOrUpdate(roomId, sd, (key, oldValue) => sd));
        }
    }
}
