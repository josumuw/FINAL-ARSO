using Usuarios.Modelo;
using Repositorio;

namespace Usuarios.Repositorio
{
    public interface IRepositorioUsuarios : IRepositorio<Usuario, string>
    {
        Usuario GetByIdOAuth2(string idOAuth2);
    }
}