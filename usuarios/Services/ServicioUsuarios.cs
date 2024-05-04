using Usuarios.Repositorio;
using Usuarios.Modelo;
using Tokens.Servicio;

namespace Usuarios.Servicio 
{
public interface IServicioUsuarios {
    string Create(string token, Usuario usuario);
    void Delete(string id);
    string Activar(string id);
    Dictionary<string, string> AutenticarLogin(string id, string password);
    Dictionary<string, string> AutenticarOAuth2(string idOAuth2);
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
    public Dictionary<string, string> AutenticarLogin(string id, string password)
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

        // Crear claims usuario.
        var claimsDict = new Dictionary<string, string>();
        claimsDict.Add("Id", usuario.Id);
        claimsDict.Add("NombreCompleto", usuario.NombreCompleto);
        claimsDict.Add("Rol", usuario.Rol.ToString());
        return claimsDict;
    }
    public Dictionary<string, string> AutenticarOAuth2(string idOAuth2)
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
        var claimsDict = new Dictionary<string, string>();
        claimsDict.Add("Id", usuario.Id);
        claimsDict.Add("NombreCompleto", usuario.NombreCompleto);
        claimsDict.Add("Rol", usuario.Rol.ToString());
        return claimsDict;
    }
    public List<Usuario> GetAll()
    {
        return repositorio.GetAll();
    }
}
}
