using Tokens.Repositorio;
using Tokens.Modelo;

namespace Tokens.Servicio 
{
public interface IServicioTokens
{
    string Get(string idUsuario);
    bool Validate(string id);
    bool Expirate(string id);
}

public class ServicioTokens : IServicioTokens
{
    private const int LongitudToken = 24;
    // Plazo de 1 hora por defecto.
    public readonly TimeSpan PlazoValidez = TimeSpan.FromHours(1);
    private readonly IRepositorioTokens repositorio;

    public ServicioTokens(IRepositorioTokens repositorio)
    {
        this.repositorio = repositorio;
    }

    public string Get(string idUsuario)
    {
        Token activo = repositorio.FindActiveTokenByUserId(idUsuario);
        if (activo != null)
        {
            throw new Exception("Ya existe un token activo para el usuario con id: " + idUsuario);
        }
    
    // Asegurarse de que la fecha de expiración tenga minutos y segundos en cero
        Token token = new Token
        {
            IdUsuario = idUsuario,
            FechaExpiracion = DateTimeOffset.Now.AddHours(1)
        };
        string id = repositorio.Add(token);
        return id;
    }

    public bool Validate(string id)
    {
        Token token = repositorio.GetById(id);
        if (token == null)
        {
            throw new Exception("No existe un token con id " + id);
        }
        DateTimeOffset d = DateTime.Now;
        if (DateTime.Now >= token.FechaExpiracion)
        {
            throw new Exception("El token ha expirado.");
        }
        return true;
    }
    public bool Expirate(string id)
    {
        Token token = repositorio.GetById(id);
        if (token == null)
        {
            throw new Exception("No existe un token con id " + id);
        }
        token.FechaExpiracion = DateTime.Now;
        repositorio.Update(token);
        return true;
    }
}
}