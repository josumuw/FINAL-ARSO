using MongoDB.Bson.Serialization.Attributes;

namespace Tokens.Modelo
{
    public class Token
    {
        [BsonId]
        [BsonRepresentation(MongoDB.Bson.BsonType.ObjectId)]
        public string Id { get; set; }
        [BsonElement("IdUsuario")]
        public string IdUsuario { get; set; }
        [BsonElement("FechaExpiracion")]
        public DateTimeOffset FechaExpiracion { get; set; }
    }
}
