using Usuarios.Repositorio;
using Usuarios.Modelo;
using Tokens.Servicio;

namespace Usuarios.Servicio 
{
public class Claims
{
    public string Id { get; set; }
    public string NombreCompleto { get; set; }
    public string Rol { get; set; }
}

public interface IServicioUsuarios {
    string Create(string token, Usuario usuario);
    void Delete(string id);
    string Activar(string id);
    Claims Autenticar(string id, string password);
    Claims AutenticarOAuth2(string idOAuth2);
    List<Usuario> GetAll();
}

public class ServicioUsuarios : IServicioUsuarios
{
    private readonly IRepositorioUsuarios repositorio;
    private readonly IServicioTokens servicioTokens;

    public ServicioUsuarios(IRepositorioUsuarios repositorio, IServicioTokens servicioTokens)
    {
        this.repositorio = repositorio;
        this.servicioTokens = servicioTokens;
    }


    public string Create(string token, Usuario usuario)
    {
        if (!servicioTokens.Validate(token))
        {
            throw new UnauthorizedAccessException("Código de activación no válido.");
        }
        usuario.Rol = Rol.USUARIO;
        String id = repositorio.Add(usuario);
        servicioTokens.Expirate(token);
        return id;
    }
    public void Delete(string id)
    {
        Usuario usuario = repositorio.GetById(id);
        if (usuario == null)
        {
            throw new KeyNotFoundException(); 
        }
        Console.Write("---------------------------------");
        Console.Write(usuario);
        repositorio.Delete(usuario);
    }
    public string Activar(string id)
    {
        if (id == null || id == "")
                throw new ArgumentException("El id del usuario no debe ser vacío");
        string idToken = servicioTokens.Get(id);
        return idToken;
    }
    public Claims Autenticar(string id, string password)
    {
        if (id == null || id == "")
                throw new ArgumentException("El id del usuario no debe ser vacío");
        if (password == null || password == "")
                throw new ArgumentException("La contraseña del usuario no debe ser vacío");

        Usuario usuario = repositorio.GetById(id);
        if (usuario == null)
            throw new Exception("No existe usuario con id " + id);

        if (!usuario.Password.Equals(password))
        {
            throw new Exception("Contraseña incorrecta.");
        }
        return new Claims
        {
            Id = usuario.Id,
            NombreCompleto = usuario.NombreCompleto,
            Rol = usuario.Rol.ToString()
        };
    }
    public Claims AutenticarOAuth2(string idOAuth2)
    {
        Usuario usuario = repositorio.GetByIdOAuth2(idOAuth2);
        if (usuario == null)
        {
            throw new Exception("No existe usuario con id " + idOAuth2);
        }
        if (!usuario.IdOAuth2.Equals(idOAuth2))
        {
            throw new Exception("IdOAuth incorrecto.");
        }
        return new Claims
        {
            Id = usuario.Id,
            NombreCompleto = usuario.NombreCompleto,
            Rol = usuario.Rol.ToString()
        };
    }
    public List<Usuario> GetAll()
    {
        return repositorio.GetAll();
    }
}
}
