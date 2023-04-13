using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace PokerFace.Web.Controllers
{
    [ApiController]
    [Route("api/[controller]/[action]")]
    public class ApiController : ControllerBase
    {
        private IMediator mediator;

        protected IMediator Mediator => mediator ??= HttpContext.RequestServices.GetService<IMediator>();

        protected async Task<ActionResult> SendMessage<TResponse>(IRequest<TResponse> message)
        {
            try
            {
                var response = await Mediator.Send(message);
                return Ok(response);
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }
    }
}
