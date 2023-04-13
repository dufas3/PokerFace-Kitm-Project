using PokerFace.Data.Entities;

namespace PokerFace.Commands.Email
{
    public interface IEmailSender
    {
        Task SendEmailAsync(Message message);
    }
}
