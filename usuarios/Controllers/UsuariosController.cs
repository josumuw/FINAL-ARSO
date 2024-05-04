
using Microsoft.AspNetCore.Mvc;
using Usuarios.Servicio;
using Usuarios.Modelo;

namespace UsuariosApi.Controllers
{
    [Route("api/usuarios")]
    [ApiController]

    public class UsuariosController : ControllerBase
    {
        private readonly IServicioUsuarios _servicio;

        public UsuariosController(IServicioUsuarios servicio)
        {
            _servicio = servicio;
        }

        [HttpPost]
        public ActionResult<String> Create(string token, [FromForm] UsuarioDTO dto)
        {
            // Verificar si el DTO contiene el atributo Password o IdOAuth2
            if (dto.Password == null && dto.IdOAuth2 == null)
            {
                // Si ninguno de los dos atributos está presente, devolver un error o realizar alguna acción apropiada
                return BadRequest("El DTO debe contener al menos uno de los atributos Password o IdOAuth2.");
            }
            Usuario usuario = dto.toEntity();
            String id = _servicio.Create(token, usuario);
            return Content("CREATE");
        }
        [HttpDelete("{id}")]
        public ActionResult<String> Delete(string id)
        {
            _servicio.Delete(id);
            return Content("DELETE");
        }
        [HttpPost("login")]
        public ActionResult<Dictionary<string, string>> AutenticarLogin([FromBody] LoginDTO request)
        {
            //TODO: COMPROBAR REQUEST PARAMS ANTES DE PASARLOS
            String id = request.Id;
            String password = request.Password;

            Dictionary<string, string> claims = _servicio.AutenticarLogin(id, password);

            if (claims != null && claims.Count > 0)
            {
                return Ok(claims);
            }
            else
            {
                return NotFound();
            }
        } 
        
        [HttpPost("oauth2")]
        public ActionResult<Dictionary<string, string>> AutenticarOAuth2([FromBody] OAuth2DTO request)
        {
            string id = request.Id;
            Dictionary<string, string> claims = _servicio.AutenticarOAuth2(id);
            if (claims != null && claims.Count > 0)
            {
                return Ok(claims);
            }
            else
            {
                return NotFound();
            }
        }

        [HttpPost("tokens")]
        public IActionResult Activar([FromForm] string id)
        {
            _servicio.Activar(id);
            return Content("ACTIVATE");
        }

        [HttpGet]
        public IActionResult GetAll() 
        {
            List<Usuario> usuarios = _servicio.GetAll();
            if (usuarios != null)
            {
                return Ok(usuarios);
            }
            else
            {
                return NotFound();
            }
        }
    }
}