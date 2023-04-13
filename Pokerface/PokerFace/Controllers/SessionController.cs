using Microsoft.AspNetCore.Mvc;
using PokerFace.Commands.Session;
using PokerFace.Web.Controllers;

namespace PokerFace.Controllers
{
    public class SessionController : ApiController
    {
        [HttpGet]
        public async Task<ActionResult> GetSession([FromQuery] GetSessionCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetSessionUsers([FromQuery] GetSessionUsersCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> LogoutSession([FromQuery] LogoutSessionCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> LogoutUser([FromQuery] LogoutUserCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetUserSelectedCards([FromQuery] GetUserSelectedCardsCommand command) => await SendMessage(command);

        [HttpPost]
        public async Task<ActionResult> SetSessionState([FromBody] SetSessionStateCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetSessionState([FromQuery] GetSessionStateCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> ClearVotes([FromQuery] ClearSessionVotesCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetActiveCards([FromQuery] GetActiveCardsCommand command) => await SendMessage(command);

        [HttpPost]
        public async Task<ActionResult> SetActiveCards([FromBody] SetActiveCardsCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetLastTimer([FromQuery] GetLastTimerCommand command) => await SendMessage(command);

        [HttpPost]
        public async Task<ActionResult> SetLastTimer([FromBody] SetLastTimerCommand command) => await SendMessage(command);
    }
}
