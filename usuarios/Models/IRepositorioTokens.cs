using Tokens.Modelo;
using Repositorio;

namespace Tokens.Repositorio
{
    public interface IRepositorioTokens : IRepositorio<Token, string>
    {
        Token FindActiveTokenByUserId(string idUsuario);
    }
}