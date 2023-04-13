using Microsoft.AspNetCore.Mvc;
using PokerFace.Commands.Cards;
using PokerFace.Commands.Session;
using PokerFace.Web.Controllers;

namespace PokerFace.Controllers
{
    public class CardController : ApiController
    {
        [HttpGet]
        public async Task<ActionResult> GetActiveCards([FromQuery] GetActiveCardsCommand command) => await SendMessage(command);

        [HttpGet]
        public async Task<ActionResult> GetCards([FromRoute] GetCardsCommand command) => await SendMessage(command);

        [HttpPost]
        public async Task<ActionResult> SetActiveCards([FromBody] SetActiveCardsCommand command) => await SendMessage(command);
    }
}