using MongoDB.Bson.Serialization.Attributes;

namespace Usuarios.Modelo
{
    public enum Rol
    {
        ADMIN,
        USUARIO
    }
public class Usuario
{
    [BsonId]
    public string Id { get; set; }
    [BsonElement("NombreCompleto")]
    public string NombreCompleto { get; set; }
    [BsonElement("Mail")]
    public string Mail { get; set; }
    [BsonElement("Telefono")]
    public string Telefono { get; set; }
    [BsonElement("DireccionPostal")]
    public string DireccionPostal { get; set; }
    [BsonRepresentation(MongoDB.Bson.BsonType.String)]
    [BsonElement("Rol")]
    public Rol Rol { get; set; }

    [BsonElement("Password")]
    public string? Password { get; set; }

    [BsonElement("IdOAuth2")]
    public string? IdOAuth2 { get; set; }
}
}
