
using Microsoft.AspNetCore.Mvc;
using Usuarios.Servicio;
using Usuarios.Modelo;
using System.Security.Claims;

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
        [HttpPost("/login")]
        public ActionResult<String> Autenticar([FromForm] UsuarioLoginDTO dto)
        {
            if (dto.IdOAuth2 != null)
            {
                Claims claims = _servicio.AutenticarOAuth2(dto.IdOAuth2);
                 if (claims != null)
                 {
                    return Ok(claims);
                 }
                 else
                 {
                    return NotFound();
                 }
            }
            else if (dto.UserName != null && dto.Password != null)
            {
                 Claims claims = _servicio.Autenticar(dto.UserName, dto.Password);
                 if (claims != null)
                 {
                    return Ok(claims);
                 }
                 else
                 {
                    return NotFound();
                 }
            }
            else
            {
                return BadRequest("El DTO debe contener al menos uno de los atributos Password o IdOAuth2.");
            }
        }

        [HttpPost("/tokens")]
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
                return Ok(usuarios); // Devuelve un código 200 OK con la lista de usuarios
            }
            else
            {
                return NotFound(); // Devuelve un código 404 Not Found si no se encontraron usuarios
            }
        }

    }
}